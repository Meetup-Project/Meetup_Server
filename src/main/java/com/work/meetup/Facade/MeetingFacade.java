package com.work.meetup.Facade;

import com.work.meetup.domain.Meeting;
import com.work.meetup.exception.customException.meetingException.MeetingNotFoundException;
import com.work.meetup.repository.MeetingRepository;
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
