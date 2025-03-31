package com.work.meetup.service.meetingService;

import com.work.meetup.Facade.UserFacade;
import com.work.meetup.domain.Meeting;
import com.work.meetup.domain.MeetingMember;
import com.work.meetup.domain.User;
import com.work.meetup.domain.enums.Role;
import com.work.meetup.dto.meetingDto.MeetingRequest;
import com.work.meetup.repository.MeetingMemberRepository;
import com.work.meetup.repository.MeetingRepository;
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
