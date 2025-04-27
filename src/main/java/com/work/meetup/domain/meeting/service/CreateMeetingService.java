package com.work.meetup.domain.meeting.service;

import com.work.meetup.domain.user.Facade.UserFacade;
import com.work.meetup.domain.meeting.entity.Meeting;
import com.work.meetup.domain.meeting.entity.MeetingMember;
import com.work.meetup.domain.user.entity.User;
import com.work.meetup.domain.user.entity.enums.Role;
import com.work.meetup.domain.meeting.dto.MeetingRequest;
import com.work.meetup.domain.meeting.repository.MeetingMemberRepository;
import com.work.meetup.domain.meeting.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingMemberRepository meetingMemberRepository;
    private final UserFacade userFacade;

    //meeting 생성 서비스
    public void createMeeting(MeetingRequest request) {
        User user = userFacade.getCurrentUser();

        //저장할 meeting 인스턴스 생성
        Meeting meeting = Meeting.builder()
                .Title(request.getTitle()) //title 값 저장
                .Description(request.getDescription()) //description 값 저장
                .Location(request.getLocation()) //location 값 저장
                .createdBy(user) //user 저장
                .build();

        //meeting 생성할 때, 오너 member도 같이 생성
        MeetingMember member = MeetingMember.builder()
                .meeting(meeting)
                .role(Role.OWNER)
                .user(user)
                .build();

        meetingRepository.save(meeting);
        meetingMemberRepository.save(member);
    }
}
