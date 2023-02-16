package com.istic.ofbapi.exception;

import com.istic.ofbapi.payload.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AccessDeniedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ApiResponse apiResponse;

    private String message;

    public AccessDeniedException(ApiResponse apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public AccessDeniedException(String message) {
        super(message);
        this.message = message;
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
    

}