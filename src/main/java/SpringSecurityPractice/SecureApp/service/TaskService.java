package SpringSecurityPractice.SecureApp.service;

import SpringSecurityPractice.SecureApp.entity.Employee;
import SpringSecurityPractice.SecureApp.entity.Task;
import SpringSecurityPractice.SecureApp.entity.requestEntity.TaskRequest;
import SpringSecurityPractice.SecureApp.repo.TaskRepo;
import SpringSecurityPractice.SecureApp.repo.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private TaskRepo taskRepo;
    private UserRepo userRepo;


    public Task createTask(TaskRequest taskRequest) {

        Employee assigned_to = userRepo.findById(taskRequest.getAssignedTo()).orElseThrow(() -> new RuntimeException("Employee not found")) ;

        Employee assigned_by = userRepo.findById(taskRequest.getAssignedBy()).orElseThrow(() -> new RuntimeException("Employee not found")) ;

        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setAssigned_to(assigned_to);
        task.setAssigned_by(assigned_by);
        task.setDue_date(taskRequest.getDueDate());


        return taskRepo.save(task);

    }

}
