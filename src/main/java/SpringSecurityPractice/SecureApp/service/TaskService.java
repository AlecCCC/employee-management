package SpringSecurityPractice.SecureApp.service;

import SpringSecurityPractice.SecureApp.entity.Employee;
import SpringSecurityPractice.SecureApp.entity.Task;
import SpringSecurityPractice.SecureApp.entity.requestEntity.TaskRequest;
import SpringSecurityPractice.SecureApp.entity.responseEntity.TaskResponse;
import SpringSecurityPractice.SecureApp.repo.TaskRepo;
import SpringSecurityPractice.SecureApp.repo.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private TaskRepo taskRepo;
    private EmployeeRepo userRepo;

    public TaskService(TaskRepo taskRepo, EmployeeRepo userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    public Task createTask(TaskRequest taskRequest) {

        Employee assigned_to = userRepo.findById(taskRequest.getAssignedTo()).orElseThrow(() -> new RuntimeException("Employee not found")) ;

        Employee assigned_by = userRepo.findById(taskRequest.getAssignedBy()).orElseThrow(() -> new RuntimeException("Employee not found")) ;

        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setAssigned_to(assigned_to);
        task.setAssigned_by(assigned_by);
        task.setDueDate(taskRequest.getDueDate());
        task.setStatus(taskRequest.getStatus());


        return taskRepo.save(task);

    }

    public List<TaskResponse> findTasksWithUsernames() {
        return taskRepo.findAllTasksWithUsernames();
    }


}
