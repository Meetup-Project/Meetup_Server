package com.work.meetup.Facade;

import com.work.meetup.domain.Meeting;
import com.work.meetup.domain.MeetingMember;
import com.work.meetup.domain.User;
import com.work.meetup.exception.customException.meetingException.MemberNotFoundException;
import com.work.meetup.repository.MeetingMemberRepository;
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
