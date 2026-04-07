package SpringSecurityPractice.SecureApp.RestControllers;

import SpringSecurityPractice.SecureApp.entity.Employee;
import SpringSecurityPractice.SecureApp.entity.Task;
import SpringSecurityPractice.SecureApp.entity.requestEntity.RegisterRequest;
import SpringSecurityPractice.SecureApp.entity.requestEntity.TaskRequest;
import SpringSecurityPractice.SecureApp.entity.responseEntity.EmployeeResponse;
import SpringSecurityPractice.SecureApp.entity.responseEntity.TaskResponse;
import SpringSecurityPractice.SecureApp.security.Jwt.JwtUtil;
import SpringSecurityPractice.SecureApp.security.Jwt.dto.LoginRequest;
import SpringSecurityPractice.SecureApp.security.Jwt.dto.LoginResponse;
import SpringSecurityPractice.SecureApp.service.TaskService;
import SpringSecurityPractice.SecureApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/security-practice")
public class RestController {

    private final UserService userService;
    private final TaskService taskService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public RestController(UserService userService, TaskService taskService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.taskService = taskService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
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
    public ResponseEntity<List<TaskResponse>> getTasksWithUsernames(@AuthenticationPrincipal UserDetails userDetails) {
       List<TaskResponse> tasks = taskService.findTasksWithUsernames();

       Employee loggedInEmployee = userService.findByUsername(userDetails.getUsername());

       if (!loggedInEmployee.getAuthority().equals("ADMIN")) {

           return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

       }

       return ResponseEntity.ok(tasks);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id,
                                                        @AuthenticationPrincipal UserDetails userDetails) {

        Employee loggedInEmployee = userService.findByUsername(userDetails.getUsername());

        String authority = loggedInEmployee.getAuthority();
        boolean isAdmin = authority.equals("ADMIN");

        boolean isOwnData = loggedInEmployee.getId().equals(id);

        if (!isAdmin && !isOwnData) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        EmployeeResponse employeeResponse = userService.getEmployeeWithTask(id);
        return ResponseEntity.ok(employeeResponse);
    }

    /// JWT Auth ///

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtUtil.generateToken(request.getUsername());

        return ResponseEntity.ok(new LoginResponse(token));

    }



}