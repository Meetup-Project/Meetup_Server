package com.work.meetup.domain.meeting.service;

import com.work.meetup.domain.meeting.Facade.MeetingFacade;
import com.work.meetup.domain.meeting.Facade.MeetingMemberFacade;
import com.work.meetup.domain.user.Facade.UserFacade;
import com.work.meetup.domain.meeting.entity.Meeting;
import com.work.meetup.domain.meeting.entity.MeetingMember;
import com.work.meetup.domain.user.entity.User;
import com.work.meetup.domain.user.entity.enums.Role;
import com.work.meetup.global.exception.customException.UnauthorizedException;
import com.work.meetup.domain.meeting.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteMeetingService {

    private final MeetingFacade meetingFacade;
    private final MeetingRepository meetingRepository;
    private final MeetingMemberFacade meetingMemberFacade;
    private final UserFacade userFacade;

    //meeting을 삭제하는 서비스
    public void deleteMeeting(Long id){
        User user = userFacade.getCurrentUser();
        Meeting meeting = meetingFacade.findById(id);
        MeetingMember member = meetingMemberFacade.getMemberByUserAndMeeting(user, meeting);

        if(Role.ADMIN != member.getRole() && Role.OWNER != member.getRole()) {
            throw new UnauthorizedException();
        }

        meetingRepository.delete(meeting);
    }


}
