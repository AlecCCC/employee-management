package SpringSecurityPractice.SecureApp.entity.responseEntity;

import java.time.LocalDate;

public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDate dueDate;
    private String assignedToUsername;
    private String assignedByUsername;

    public TaskResponse(Long id, String title, String description, String status, LocalDate dueDate, String assignedToUsername, String assignedByUsername) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.assignedToUsername = assignedToUsername;
        this.assignedByUsername = assignedByUsername;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getAssignedToUsername() {
        return assignedToUsername;
    }

    public String getAssignedByUsername() {
        return assignedByUsername;
    }
}
