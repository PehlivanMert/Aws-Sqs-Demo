package org.pehlivan.mert.awssqs.exception;

public class UserAlreadyRegistered extends BaseException {
    public UserAlreadyRegistered(String message) {
        super(message);
    }
}