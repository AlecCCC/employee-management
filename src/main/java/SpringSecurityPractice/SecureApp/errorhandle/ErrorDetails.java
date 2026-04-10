package SpringSecurityPractice.SecureApp.errorhandle;

public class ErrorDetails {

    private int status;
    private String error;
    private String message;

    public ErrorDetails(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ErrorDetails() {

    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
