package com.work.meetup.controller;

import com.work.meetup.dto.meetingDto.MeetingRequest;
import com.work.meetup.service.meetingService.CreateMeetingService;
import com.work.meetup.service.meetingService.DeleteMeetingService;
import com.work.meetup.service.meetingService.QueryMeetingService;
import com.work.meetup.service.meetingService.UpdateMeetingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MeetingController {
    public final CreateMeetingService createMeetingService;
    public final QueryMeetingService queryMeetingService;
    public final UpdateMeetingService updateMeetingService;
    public final DeleteMeetingService deleteMeetingService;

    @PostMapping("/meeting/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMeeting(@RequestBody @Valid MeetingRequest request) {
        createMeetingService.createMeeting(request);
    }

    @PostMapping("/meeting/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void queryMeeting(@PathVariable Long id) {
        queryMeetingService.queryMeeting(id);
    }

    @PostMapping("/meetings")
    @ResponseStatus(HttpStatus.OK)
    public void queryMeetingList() {
        queryMeetingService.queryMeetingList();
    }

    @PatchMapping("/meeting/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMeeting(@RequestBody MeetingRequest request, @PathVariable Long id) {
        updateMeetingService.updateMeeting(request, id);
    }

    @DeleteMapping("/meeting/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMeeting(@PathVariable Long id) {
        deleteMeetingService.deleteMeeting(id);
    }
}
