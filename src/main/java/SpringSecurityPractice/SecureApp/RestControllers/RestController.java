package SpringSecurityPractice.SecureApp.RestControllers;

import SpringSecurityPractice.SecureApp.entity.Task;
import SpringSecurityPractice.SecureApp.entity.requestEntity.RegisterRequest;
import SpringSecurityPractice.SecureApp.entity.requestEntity.TaskRequest;
import SpringSecurityPractice.SecureApp.entity.responseEntity.TaskResponse;
import SpringSecurityPractice.SecureApp.service.TaskService;
import SpringSecurityPractice.SecureApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/security-practice")
public class RestController {

    private final UserService userService;
    private final TaskService taskService;

    public RestController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/hello")
    public String helloEndpoint(@AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            return "Hello Guest";
        }
        return "Hello " + user.getUsername();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/task")
    public ResponseEntity<Task> saveTask(@RequestBody TaskRequest request) {
        Task task = taskService.createTask(request);
        return ResponseEntity.ok(task);

    }

    @GetMapping("/task")
    public ResponseEntity<List<TaskResponse>> getTasksWithUsernames() {
       List<TaskResponse> tasks = taskService.findTasksWithUsernames();

       return ResponseEntity.ok(tasks);
    }



}