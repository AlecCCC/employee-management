package SpringSecurityPractice.SecureApp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Table(name="task")
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;

    @Column(name = "due_date")
    private LocalDate due_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to", nullable = false)
    private Employee assigned_to;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", nullable = false)
    private Employee assigned_by;

    public Task() {
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

    public LocalDate getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }

    public Employee getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(Employee assigned_to) {
        this.assigned_to = assigned_to;
    }

    public Employee getAssigned_by() {
        return assigned_by;
    }

    public void setAssigned_by(Employee assigned_by) {
        this.assigned_by = assigned_by;
    }
}
