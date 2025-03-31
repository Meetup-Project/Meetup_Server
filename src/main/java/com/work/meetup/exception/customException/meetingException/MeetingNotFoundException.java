package com.work.meetup.exception.customException.meetingException;

import com.work.meetup.exception.customException.ErrorCode;
import com.work.meetup.exception.customException.GlobalException;

public class MeetingNotFoundException extends GlobalException {
    public static final GlobalException EXCEPTION = new MeetingNotFoundException();

    public MeetingNotFoundException() {
        super(ErrorCode.MEETING_NOT_FOUND);
    }
}
