package com.webbfontaine.task.githubanalytics.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler({GithubServerException.class})
    public ResponseEntity<String> handleServerError(GithubServerException ex) {
        log.error("Exception during request processing. Message : {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler({GithubClientException.class})
    public ResponseEntity<String> handleClientError(GithubClientException ex) {
        log.error("Exception during request processing. Message : {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> illegalArgumentHandler(WebExchangeBindException ex) {
        log.error("Exception during request processing. Message : {}", "Illegal argumants");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentHandler(IllegalArgumentException ex) {
        log.error("Exception during request processing. Message : {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> allExceptionsHandler(Exception ex) {
        log.error("Exception during request processing. Message : {}", ex.getStackTrace());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error please contact the administrator");
    }
}
