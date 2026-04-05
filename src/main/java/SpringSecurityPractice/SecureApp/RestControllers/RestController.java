package SpringSecurityPractice.SecureApp.RestControllers;

import SpringSecurityPractice.SecureApp.entity.Task;
import SpringSecurityPractice.SecureApp.entity.requestEntity.RegisterRequest;
import SpringSecurityPractice.SecureApp.entity.requestEntity.TaskRequest;
import SpringSecurityPractice.SecureApp.service.TaskService;
import SpringSecurityPractice.SecureApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/security-practice")
public class RestController {

    private final UserService userService;
    private final TaskService taskService;

    public RestController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<String> saveTask(@RequestBody TaskRequest request) {

    }

}