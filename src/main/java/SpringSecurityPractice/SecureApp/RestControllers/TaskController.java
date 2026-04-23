package SpringSecurityPractice.SecureApp.RestControllers;

import SpringSecurityPractice.SecureApp.entity.Employee;
import SpringSecurityPractice.SecureApp.entity.Task;
import SpringSecurityPractice.SecureApp.entity.requestEntity.TaskRequest;
import SpringSecurityPractice.SecureApp.entity.responseEntity.TaskResponse;
import SpringSecurityPractice.SecureApp.service.TaskService;
import SpringSecurityPractice.SecureApp.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/security-practice")
public class TaskController {

    private TaskService taskService;
    private EmployeeService employeeService;

    public TaskController(TaskService taskService, EmployeeService employeeService) {
        this.taskService = taskService;
        this.employeeService = employeeService;
    }


    @PostMapping("/task")
    public ResponseEntity<Task> saveTask(@RequestBody TaskRequest request) {
        Task task = taskService.createTask(request);
        return ResponseEntity.ok(task);

    }

    @GetMapping("/task")
    public ResponseEntity<Page<TaskResponse>> getTasksWithUsernames(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(value="assignedto", required = false) String assigned_to,
            @RequestParam(value="assignedby",required = false) String assigned_by) {

        Employee loggedInEmployee = employeeService.findByUsername(userDetails.getUsername());

        if (!loggedInEmployee.getAuthority().equals("ADMIN")) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        }

        return ResponseEntity.ok(taskService.findTasksFiltered(status, assigned_to, assigned_by, page, size));
    }

    @GetMapping("/tasks/overdue")
    public ResponseEntity<Page<TaskResponse>> getOverdueTasks(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Employee loggedInEmployee = employeeService.findByUsername(userDetails.getUsername());

        if (!loggedInEmployee.getAuthority().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(taskService.findOverdueTasks(page, size));
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id,
                                                    @AuthenticationPrincipal UserDetails userDetails) {

        Employee loggedInEmployee = employeeService.findByUsername(userDetails.getUsername());
        TaskResponse task = taskService.getTaskById(id);

        boolean isAdmin = loggedInEmployee.getAuthority().equals("ADMIN");
        boolean isAssigned = task.getAssignedToUsername().equals(loggedInEmployee.getUsername());

        if (!isAdmin && !isAssigned) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(task);

    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.ok("Task with id " + id + " was deleted");
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<Task> updateTask(@Valid @PathVariable Long id, @RequestBody TaskRequest taskRequest
    , @AuthenticationPrincipal UserDetails userDetails) {
        Employee loggedInEmployee = employeeService.findByUsername(userDetails.getUsername());

        if (!loggedInEmployee.getAuthority().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Task updatedTask = taskService.updateTask(id, taskRequest);
        return ResponseEntity.ok(updatedTask);

    }

}
