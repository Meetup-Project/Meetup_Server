package com.work.meetup.global.exception.customException.meetingException;

import com.work.meetup.global.exception.customException.ErrorCode;
import com.work.meetup.global.exception.customException.GlobalException;

public class MeetingNotFoundException extends GlobalException {
    public static final GlobalException EXCEPTION = new MeetingNotFoundException();

    public MeetingNotFoundException() {
        super(ErrorCode.MEETING_NOT_FOUND);
    }
}
