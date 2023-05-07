package itu.s6.tpseo.framework.springutils.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorDisplay {

    private Integer code;
    private String message;

    public ErrorDisplay(HttpStatus code, String message) {
        this.code = code.value();
        this.message = message;
    }

    public ErrorDisplay() {
    }

    public ErrorDisplay(HttpStatus code, Exception e) {
        this.code = code.value();
        this.message = e.getMessage();
    }

    public static ResponseEntity<?> returnError (HttpStatus code, String message) {
        return new ResponseEntity<>(new ErrorDisplay(code, message), code);
    }

    public static ResponseEntity<?> returnError (HttpStatus code, Exception e) {
        return returnError(code, e.getMessage());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
