package com.work.meetup.dto.meetingDto;

import com.work.meetup.domain.Meeting;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MeetingListQueryResponse {
    private String Title;
    private LocalDateTime MeetingTime;

    public MeetingListQueryResponse(Meeting meeting) {
        Title = meeting.getTitle();
        MeetingTime = meeting.getMeetingTime();
    }
}
