package com.work.meetup.domain.meeting.service;

import com.work.meetup.domain.meeting.Facade.MeetingFacade;
import com.work.meetup.domain.meeting.Facade.MeetingMemberFacade;
import com.work.meetup.domain.user.Facade.UserFacade;
import com.work.meetup.domain.meeting.entity.Meeting;
import com.work.meetup.domain.meeting.entity.MeetingMember;
import com.work.meetup.domain.user.entity.User;
import com.work.meetup.domain.meeting.dto.MeetingListQueryResponse;
import com.work.meetup.domain.meeting.dto.MeetingQueryResponse;
import com.work.meetup.global.exception.customException.UnauthorizedException;
import com.work.meetup.domain.meeting.repository.MeetingMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryMeetingService {

    private final MeetingFacade meetingFacade;
    private final MeetingMemberFacade meetingMemberFacade;
    private final MeetingMemberRepository meetingMemberRepository;
    private final UserFacade userFacade;

    //meeting의 상세 정보를 조회하는 서비스
    @Transactional(readOnly = true)
    public MeetingQueryResponse queryMeeting(Long id) {
        User user = userFacade.getCurrentUser();
        Meeting meeting = meetingFacade.findById(id);

        MeetingMember member = Optional.ofNullable(meetingMemberFacade.getMemberByUserAndMeeting(user, meeting))
                .orElseThrow(UnauthorizedException::new);


        return MeetingQueryResponse.builder()
                .Title(meeting.getTitle())
                .Description(meeting.getDescription())
                .Location(meeting.getLocation())
                .MeetingTime(meeting.getMeetingTime())
                .createdBy(meeting.getCreatedBy().getName())
                .CreatedAt(meeting.getCreatedAt())
                .UpdatedAt(meeting.getUpdatedAt())
                .build();
    }

    //자신이 속해있는 여러 meeting을 목록으로 조회하는 서비스
    @Transactional(readOnly = true)
    public List<MeetingListQueryResponse> queryMeetingList() {
        User user = userFacade.getCurrentUser();
        LocalDateTime now = LocalDateTime.now();

        List<MeetingMember> meetingMembers = meetingMemberRepository.findByUser(user);

        return meetingMembers.stream()
                .map(meetingMember -> new MeetingListQueryResponse(meetingMember.getMeeting()))
                .filter(meeting -> meeting.getMeetingTime().isAfter(now)) // 현재 시간 이후의 모임만 남김
                .sorted(Comparator.comparing(MeetingListQueryResponse::getMeetingTime)) // 최신 날짜와 가까운 순으로 정렬 (오름차순)
                .collect(Collectors.toList());
    }
}
