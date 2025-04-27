package com.work.meetup.domain.meeting.Facade;

import com.work.meetup.domain.meeting.entity.Meeting;
import com.work.meetup.domain.meeting.entity.MeetingMember;
import com.work.meetup.domain.user.entity.User;
import com.work.meetup.global.exception.customException.meetingException.MemberNotFoundException;
import com.work.meetup.domain.meeting.repository.MeetingMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeetingMemberFacade {

    private final MeetingMemberRepository meetingMemberRepository;

    public MeetingMember getMemberById(Long id) {
        return meetingMemberRepository.findById(id)
                .orElseThrow(()-> MemberNotFoundException.EXCEPTION);
    }

    public MeetingMember getMemberByUserAndMeeting(User user, Meeting meeting) {
        return meetingMemberRepository.findByUserAndMeeting(user, meeting)
                .orElseThrow(()-> MemberNotFoundException.EXCEPTION);
    }
}
