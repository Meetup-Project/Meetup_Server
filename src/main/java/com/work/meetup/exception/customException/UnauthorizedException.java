package com.work.meetup.exception.customException;

public class UnauthorizedException extends GlobalException {
    public static final GlobalException EXCEPTION = new UnauthorizedException();

    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}
