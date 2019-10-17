package com.stackroute.assignment.exception;

public class TrackAlreadyExistException extends RuntimeException {



    public TrackAlreadyExistException(){
        super("Track already exists");
    }


}
