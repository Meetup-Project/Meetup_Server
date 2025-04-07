package com.work.meetup.service.meetingService;

import com.work.meetup.Facade.MeetingFacade;
import com.work.meetup.Facade.MeetingMemberFacade;
import com.work.meetup.Facade.UserFacade;
import com.work.meetup.domain.Meeting;
import com.work.meetup.domain.MeetingMember;
import com.work.meetup.domain.User;
import com.work.meetup.domain.enums.Role;
import com.work.meetup.dto.meetingDto.MeetingRequest;
import com.work.meetup.exception.customException.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateMeetingService {

    private final MeetingFacade meetingFacade;
    private final MeetingMemberFacade meetingMemberFacade;
    private final UserFacade userFacade;

    //meeting의 정보를 수정하는 서비스
    public void updateMeeting(MeetingRequest request, Long id) {
        User user = userFacade.getCurrentUser();
        Meeting meeting = meetingFacade.findById(id);
        MeetingMember member = meetingMemberFacade.getMemberByUserAndMeeting(user, meeting);

        if(Role.ADMIN != member.getRole() && Role.OWNER != member.getRole()) {
            throw new UnauthorizedException();
        }

        // 기존 값을 유지하면서 새로운 값이 있으면 업데이트
        meeting.update(
                Optional.ofNullable(request.getTitle()).orElse(meeting.getTitle()),
                Optional.ofNullable(request.getDescription()).orElse(meeting.getDescription()),
                Optional.ofNullable(request.getLocation()).orElse(meeting.getLocation()),
                Optional.ofNullable(request.getMeetingTime()).orElse(meeting.getMeetingTime())
        );
    }
}
