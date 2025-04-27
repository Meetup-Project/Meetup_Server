package com.work.meetup.domain.meeting.Facade;

import com.work.meetup.domain.meeting.entity.Meeting;
import com.work.meetup.global.exception.customException.meetingException.MeetingNotFoundException;
import com.work.meetup.domain.meeting.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeetingFacade {

    private final MeetingRepository meetingRepository;

    public Meeting findById(Long id) {
        return meetingRepository.findById(id)
                .orElseThrow(() -> MeetingNotFoundException.EXCEPTION);
    }
}
