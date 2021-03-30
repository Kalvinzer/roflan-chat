package kvinz.roflanchat.controller.error_handler.custom_errors;

public class UserNotFoundException  extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
