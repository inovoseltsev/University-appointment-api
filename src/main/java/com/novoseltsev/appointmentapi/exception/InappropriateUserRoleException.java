package com.novoseltsev.appointmentapi.exception;

public class InappropriateUserRoleException extends RuntimeException {
    public InappropriateUserRoleException(String message) {
        super(message);
    }
}