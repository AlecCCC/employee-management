package SpringSecurityPractice.SecureApp.errorhandle.exceptions;

public class EmailExistsException extends RuntimeException{

    public EmailExistsException(String email) {
        super("Email already Exist: " + email);
    }

}
