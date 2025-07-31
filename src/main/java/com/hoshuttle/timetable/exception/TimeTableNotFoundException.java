package com.hoshuttle.timetable.exception;



public class TimeTableNotFoundException extends RuntimeException {
    public TimeTableNotFoundException(String message) {
        super(message);
    }
}
