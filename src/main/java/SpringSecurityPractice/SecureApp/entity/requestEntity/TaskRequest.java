package SpringSecurityPractice.SecureApp.entity.requestEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class TaskRequest {


    @NotBlank(message = "title is required")
    private String title;
    @NotBlank(message = "description is required")
    private String description;
    @NotNull(message = "status is required")
    private String status;
    @NotNull(message = "dueDate is required")
    private LocalDate dueDate;
    @NotNull(message = "assignedTo is required")
    private Long assignedTo;
    @NotNull(message = "assignedBy is required")
    private Long assignedBy;

    public TaskRequest() {
    }

    public TaskRequest(String title, String description, String status, LocalDate dueDate, Long assignedTo, Long assignedBy) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.assignedTo = assignedTo;
        this.assignedBy = assignedBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Long getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Long assignedBy) {
        this.assignedBy = assignedBy;
    }
}
