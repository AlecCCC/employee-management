package SpringSecurityPractice.SecureApp.service;

import SpringSecurityPractice.SecureApp.repo.TaskRepo;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }



}
