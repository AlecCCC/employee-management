package SpringSecurityPractice.SecureApp.entity.responseEntity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"id", "username", "firstName", "lastName", "email", "authority", "assignedTasks"})

public class EmployeeResponse {

    private Long id;
    private String username;
    private String authority;
    private String firstName;
    private String lastName;
    private String email;


    private List<TaskResponse> assignedTasks;

    public EmployeeResponse(Long id, String username, String authority, String firstName,
                            String lastName, String email, List<TaskResponse> assignedTasks) {
        this.id = id;
        this.username = username;
        this.authority = authority;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;  // ← add this
        this.assignedTasks = assignedTasks;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthority() {
        return authority;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<TaskResponse> getAssignedTasks() {
        return assignedTasks;
    }

    public String getEmail() {
        return email;
    }
}
