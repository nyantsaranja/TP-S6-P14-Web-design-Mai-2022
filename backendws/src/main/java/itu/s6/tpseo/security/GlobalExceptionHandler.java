package itu.s6.tpseo.security;

import itu.s6.tpseo.framework.springutils.util.ErrorDisplay;
import itu.s6.tpseo.framework.springutils.util.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        HttpStatus status=null;
        // check if the exception has an other exception as cause
        while (e.getCause() != null) {
            e = (Exception) e.getCause();
        }
        if (e instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof IllegalStateException) {
            status = HttpStatus.CONFLICT;
        } else if (e instanceof NullPointerException) {
            status = HttpStatus.NOT_FOUND;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        e.printStackTrace();
        return ErrorDisplay.returnError(status, e.getMessage());
    }
}
