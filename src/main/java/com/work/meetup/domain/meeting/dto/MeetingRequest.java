package com.work.meetup.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRequest {
    private String Title;
    private String Description;
    private String Location;
    private LocalDateTime MeetingTime;
}
