package SpringSecurityPractice.SecureApp.service;

import SpringSecurityPractice.SecureApp.entity.Employee;
import SpringSecurityPractice.SecureApp.entity.requestEntity.RegisterRequest;
import SpringSecurityPractice.SecureApp.entity.responseEntity.EmployeeResponse;
import SpringSecurityPractice.SecureApp.entity.responseEntity.TaskResponse;
import SpringSecurityPractice.SecureApp.repo.TaskRepo;
import SpringSecurityPractice.SecureApp.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final TaskRepo taskRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, TaskRepo taskRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterRequest request) {
        Employee employee = new Employee();
        employee.setUsername(request.getUsername());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setAuthority(request.getAuthority());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        userRepo.save(employee);
    }

    public EmployeeResponse getEmployeeWithTask(Long id) {

        Employee employee = userRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee with Id of " + id + " not found"));

        List<TaskResponse> tasks = taskRepo.findTasksAssignedToById(id);

        return new EmployeeResponse(
                employee.getId(),
                employee.getUsername(),
                employee.getAuthority(),
                employee.getFirstName(),
                employee.getLastName(),
                tasks
        );

    }


}
