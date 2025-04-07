package com.work.meetup.exception.customException;

public class UserNotFoundException extends GlobalException {
    public static final GlobalException EXCEPTION = new UserNotFoundException();

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
