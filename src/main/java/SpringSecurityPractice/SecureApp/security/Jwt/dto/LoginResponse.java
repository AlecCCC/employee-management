package SpringSecurityPractice.SecureApp.security.Jwt.dto;

public class LoginResponse {

    private String token;
    private String username;
    private Long id;
    private String authority;

    public LoginResponse(String token, String username, Long id, String authority) {
        this.token = token;
        this.username = username;
        this.id = id;
        this.authority = authority;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getAuthority() {
        return authority;
    }
}
