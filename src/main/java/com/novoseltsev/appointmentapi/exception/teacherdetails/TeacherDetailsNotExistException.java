package com.novoseltsev.appointmentapi.exception.teacherdetails;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TeacherDetailsNotExistException extends RuntimeException {

    public TeacherDetailsNotExistException() {
    }

    public TeacherDetailsNotExistException(String message) {
        super(message);
    }
}
