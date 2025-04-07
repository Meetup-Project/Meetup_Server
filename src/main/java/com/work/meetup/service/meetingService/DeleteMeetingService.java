package com.work.meetup.service.meetingService;

import com.work.meetup.Facade.MeetingFacade;
import com.work.meetup.Facade.MeetingMemberFacade;
import com.work.meetup.Facade.UserFacade;
import com.work.meetup.domain.Meeting;
import com.work.meetup.domain.MeetingMember;
import com.work.meetup.domain.User;
import com.work.meetup.domain.enums.Role;
import com.work.meetup.exception.customException.UnauthorizedException;
import com.work.meetup.repository.MeetingRepository;
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
