package SpringSecurityPractice.SecureApp.errorhandle;

import SpringSecurityPractice.SecureApp.errorhandle.exceptions.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> TaskNotFoundException(TaskNotFoundException taskNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetails(404, "Not Found", taskNotFoundException.getMessage()));
    }

}
