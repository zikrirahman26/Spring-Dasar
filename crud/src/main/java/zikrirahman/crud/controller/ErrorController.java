package zikrirahman.crud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolationException;
import zikrirahman.crud.dto.WebReponse;

@RestControllerAdvice
public class ErrorController {
    
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebReponse<String>> statusExection(ResponseStatusException exception){
        return ResponseEntity.status(exception.getStatusCode()).body(WebReponse.<String>builder().errors(exception.getReason()).build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebReponse<String>> constraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(WebReponse.<String>builder().errors(exception.getMessage()).build());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<WebReponse<String>> nullException(NullPointerException exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(WebReponse.<String>builder().errors(exception.getMessage()).build());
    }
}
