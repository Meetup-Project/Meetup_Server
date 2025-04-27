package com.work.meetup.domain.meeting.dto;

import com.work.meetup.domain.meeting.entity.Meeting;
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
