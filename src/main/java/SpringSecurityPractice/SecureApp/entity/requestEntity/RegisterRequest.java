package SpringSecurityPractice.SecureApp.entity.requestEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotEmpty(message = "Username Required")
    @Size(min = 3, max = 15, message = "Username must be 3 to 15 characters max")
    private String username;
    @NotEmpty(message = "Password Required")
    @Size(min=5, max=20, message = "Password must be 5 to 20 characters max")
    private String password;
    private String authority;
    @NotBlank(message="First name required")
    private String firstName;
    @NotBlank(message="Last name required")

    private String lastName;
    @NotBlank(message = "Email Required")
    @Email(message = "Not formatted correctly")
    private String email;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAuthority() { return authority; }
    public void setAuthority(String authority) { this.authority = authority; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}