package SpringSecurityPractice.SecureApp.service;

import SpringSecurityPractice.SecureApp.entity.Employee;
import SpringSecurityPractice.SecureApp.entity.Task;
import SpringSecurityPractice.SecureApp.entity.requestEntity.TaskRequest;
import SpringSecurityPractice.SecureApp.entity.responseEntity.TaskResponse;
import SpringSecurityPractice.SecureApp.errorhandle.exceptions.EmployeeNotFoundException;
import SpringSecurityPractice.SecureApp.errorhandle.exceptions.TaskNotFoundException;
import SpringSecurityPractice.SecureApp.repo.TaskRepo;
import SpringSecurityPractice.SecureApp.repo.EmployeeRepo;
import SpringSecurityPractice.SecureApp.specifications.TaskSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private TaskRepo taskRepo;
    private EmployeeRepo userRepo;

    public TaskService(TaskRepo taskRepo, EmployeeRepo userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    public TaskResponse createTask(TaskRequest taskRequest) {

        Employee assigned_to = userRepo.findById(taskRequest.getAssignedTo()).orElseThrow(() -> new EmployeeNotFoundException("Employee not found")) ;

        Employee assigned_by = userRepo.findById(taskRequest.getAssignedBy()).orElseThrow(() -> new EmployeeNotFoundException("Employee not found")) ;

        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setAssignedTo(assigned_to);
        task.setAssignedBy(assigned_by);
        task.setDueDate(taskRequest.getDueDate());
        task.setStatus(taskRequest.getStatus());


        Task saved = taskRepo.save(task);

        return new TaskResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getStatus(),
                saved.getDueDate(),
                saved.getAssignedTo().getUsername(),
                saved.getAssignedBy().getUsername()
        );

    }


    public Page<TaskResponse> findTasksWithUsernames(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepo.findAllTasksWithUsernames(pageable);
    }

    public boolean deleteById(long id) {
        if (taskRepo.existsById(id)) {
            taskRepo.deleteById(id);
            return true;
        }
        return false;
    }



    @Transactional
    public Page<TaskResponse> findTasksFiltered(String status, String assigned_to, String assigned_by, int page, int size) {

        System.out.println("Controller received - status: " + status);
        System.out.println("Controller received - assigned_to: " + assigned_to);
        System.out.println("Controller received - assigned_by: " + assigned_by);

        Specification<Task> specification = (root, query, builder) -> null;

        if (status != null) {
            specification = specification.and(TaskSpecification.byStatus(status));
        }

        if (assigned_to != null) {
            specification = specification.and(TaskSpecification.byAssignedToUsername(assigned_to));
        }

        if (assigned_by != null) {
            specification = specification.and(TaskSpecification.byAssignedByUsername(assigned_by));
        }

        Page<Task> tasks = taskRepo.findAll(specification, PageRequest.of(page, size));

        // map Task to TaskResponse
        return tasks.map(task -> new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                task.getAssignedTo().getUsername(),
                task.getAssignedBy().getUsername()
        ));
    }

    public Page<TaskResponse> findOverdueTasks(int page, int size) {

        Specification<Task> specification = Specification.where(TaskSpecification.isOverdueAndTodo());
        Page<Task> tasks = taskRepo.findAll(specification, PageRequest.of(page, size));

        return tasks.map(task -> new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                task.getAssignedTo().getUsername(),
                task.getAssignedBy().getUsername()
        ));
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepo.findById(id)
                .orElseThrow(()-> new TaskNotFoundException(id));

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                task.getAssignedTo().getUsername(),
                task.getAssignedBy().getUsername()
        );

    }

    public Task updateTask(Long id, TaskRequest taskRequest) {

        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (taskRequest.getTitle() == null || taskRequest.getTitle().isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        if (taskRequest.getDescription() == null) {
            throw new IllegalArgumentException("description is required");
        }
        if (taskRequest.getStatus() == null) {
            throw new IllegalArgumentException("status is required");
        }
        if (taskRequest.getDueDate() == null) {
            throw new IllegalArgumentException("dueDate is required");
        }
        if (taskRequest.getAssignedBy() == null) {
            throw new IllegalArgumentException("assignedBy is required");
        }
        if (taskRequest.getAssignedTo() == null) {
            throw new IllegalArgumentException("assignedTo is required");
        }

        Employee assignedBy = userRepo.findById(taskRequest.getAssignedBy())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Employee assignedTo = userRepo.findById(taskRequest.getAssignedTo())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        task.setDueDate(taskRequest.getDueDate());
        task.setAssignedBy(assignedBy);
        task.setAssignedTo(assignedTo);

        return taskRepo.save(task);
    }
}
