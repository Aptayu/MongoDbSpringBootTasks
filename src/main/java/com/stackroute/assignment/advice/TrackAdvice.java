package com.stackroute.assignment.advice;
import com.stackroute.assignment.exception.TrackAlreadyExistException;
import com.stackroute.assignment.exception.TrackNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TrackAdvice {
    @ResponseBody
    @ExceptionHandler(TrackAlreadyExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String trackExistsAdvice(TrackAlreadyExistException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TrackNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String NotFoundAdvice(TrackNotFoundException ex){
        return ex.getMessage();
    }
}
