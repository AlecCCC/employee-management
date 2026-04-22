package SpringSecurityPractice.SecureApp.errorhandle;

import SpringSecurityPractice.SecureApp.errorhandle.exceptions.EmailExistsException;
import SpringSecurityPractice.SecureApp.errorhandle.exceptions.EmployeeNotFoundException;
import SpringSecurityPractice.SecureApp.errorhandle.exceptions.TaskNotFoundException;
import SpringSecurityPractice.SecureApp.errorhandle.exceptions.UsernameExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandle {


    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleTaskNotFoundException(TaskNotFoundException taskNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetails(404, "Not Found", taskNotFoundException.getMessage()));
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ErrorDetails> handleUsernameExistsException(UsernameExistsException usernameExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(409, "Conflict", usernameExistsException.getMessage()));
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ErrorDetails> handleEmailExistsException(EmailExistsException emailExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails(409, "Conflict", emailExistsException.getMessage()));
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDetails(404, "Not Found", ex.getMessage()));
    }



    /// ENTITY ERRORS

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

}
