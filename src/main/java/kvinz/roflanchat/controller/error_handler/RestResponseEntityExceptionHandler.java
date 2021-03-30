package kvinz.roflanchat.controller.error_handler;

import kvinz.roflanchat.controller.error_handler.custom_errors.BadRequestException;
import kvinz.roflanchat.controller.error_handler.custom_errors.InvalidTokenExeption;
import kvinz.roflanchat.controller.error_handler.custom_errors.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.NotActiveException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<CustomErrorResponce> handleBadRequestException(BadRequestException exception){
        return new ResponseEntity<>(new CustomErrorResponce(exception.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidTokenExeption.class})
    public ResponseEntity<CustomErrorResponce> handleInvalidTokenExeption(InvalidTokenExeption exception){
        return new ResponseEntity<>(new CustomErrorResponce(exception.getMessage()),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<CustomErrorResponce> handleUserNotFoundException(UserNotFoundException exception){
        return new ResponseEntity<>(new CustomErrorResponce(exception.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotActiveException.class})
    public ResponseEntity<CustomErrorResponce> handleUserNotActive(NotActiveException exception){
        return new ResponseEntity<>(new CustomErrorResponce(exception.getMessage()),HttpStatus.BAD_REQUEST);
    }
}
