package com.work.meetup.dto.meetingDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingQueryResponse {
    private String Title;
    private String Description;
    private String Location;
    private LocalDateTime MeetingTime;
    private String createdBy;
    private LocalDateTime CreatedAt;
    private LocalDateTime UpdatedAt;
}
