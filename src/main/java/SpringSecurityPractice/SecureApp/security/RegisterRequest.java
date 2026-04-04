package SpringSecurityPractice.SecureApp.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {
    private String username;
    private String password;
    private String authority;
    private String firstName;
    private String lastName;
    private String email;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAuthority() { return authority; }
    public void setAuthority(String authority) { this.authority = authority; }

    @JsonProperty("first_name")
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    @JsonProperty("last_name")
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}