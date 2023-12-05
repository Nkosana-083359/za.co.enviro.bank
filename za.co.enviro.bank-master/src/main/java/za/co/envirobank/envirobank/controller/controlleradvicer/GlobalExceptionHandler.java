package za.co.envirobank.envirobank.controller.controlleradvicer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import za.co.envirobank.envirobank.exceptions.AmountException;
import za.co.envirobank.envirobank.exceptions.EntityNotFoundException;
import za.co.envirobank.envirobank.exceptions.InputException;
import za.co.envirobank.envirobank.exceptions.ResourceExpired;
import za.co.envirobank.envirobank.model.entities.ErrorMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleGlobalException(EntityNotFoundException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage(),HttpStatus.NOT_FOUND);
        return ResponseEntity.status(message.getErrorStatus()).body(message);
    }

    @ExceptionHandler(InputException.class)
    public ResponseEntity<?> handleInputException(InputException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage(),HttpStatus.CONFLICT);
        return ResponseEntity.status(message.getErrorStatus()).body(message);
    }

    @ExceptionHandler(AmountException.class)
    public ResponseEntity<?> handleAmountException(AmountException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage(),HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(message.getErrorStatus()).body(message);
    }



    @ExceptionHandler(ResourceExpired.class)
    public ResponseEntity<?> resourceExpired(ResourceExpired ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage(),HttpStatus.GONE);
        return ResponseEntity.status(message.getErrorStatus()).body(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        List<Object> list = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            list.add(error);
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}