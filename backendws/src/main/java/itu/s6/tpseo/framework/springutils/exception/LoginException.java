package itu.s6.tpseo.framework.springutils.exception;

public class LoginException extends CustomException{
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
