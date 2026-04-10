package SpringSecurityPractice.SecureApp.errorhandle.exceptions;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(Long id) {
        super("Task not Found: " + id);
    }
}
