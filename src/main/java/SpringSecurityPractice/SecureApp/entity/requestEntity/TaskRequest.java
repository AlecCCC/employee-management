package SpringSecurityPractice.SecureApp.entity.requestEntity;

import java.time.LocalDate;

public class TaskRequest {


    private String title;
    private String description;
    private String status;
    private LocalDate dueDate;
    private Long assignedTo;
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
