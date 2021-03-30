package kvinz.roflanchat.controller.error_handler.custom_errors;


public class InvalidTokenExeption extends RuntimeException{
    public InvalidTokenExeption(String message) {
        super(message);
    }
}
