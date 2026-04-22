package SpringSecurityPractice.SecureApp.errorhandle.exceptions;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Employee with ID " + id + " not found");
    }

    public EmployeeNotFoundException(String username) {
        super("Employee with username '" + username + "' not found");
    }

}
