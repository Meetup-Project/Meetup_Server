package com.work.meetup.exception.customException.meetingException;

import com.work.meetup.exception.customException.ErrorCode;
import com.work.meetup.exception.customException.GlobalException;

public class MemberNotFoundException extends GlobalException {
    public static final GlobalException EXCEPTION = new MemberNotFoundException();

    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
