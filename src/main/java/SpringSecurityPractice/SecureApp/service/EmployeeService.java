package SpringSecurityPractice.SecureApp.service;

import SpringSecurityPractice.SecureApp.entity.Employee;
import SpringSecurityPractice.SecureApp.entity.requestEntity.RegisterRequest;
import SpringSecurityPractice.SecureApp.entity.responseEntity.EmployeeResponse;
import SpringSecurityPractice.SecureApp.entity.responseEntity.TaskResponse;
import SpringSecurityPractice.SecureApp.repo.EmployeeRepo;
import SpringSecurityPractice.SecureApp.repo.TaskRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepo userRepo;
    private final TaskRepo taskRepo;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepo userRepo, TaskRepo taskRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public Employee findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public void registerUser(RegisterRequest request) {
        Employee employee = new Employee();
        employee.setUsername(request.getUsername());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setAuthority("USER");
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        userRepo.save(employee);
    }

    public List<EmployeeResponse> getEmployees() {

        List<Employee> employees = userRepo.findAll();

        List<EmployeeResponse> processedEmployees = new ArrayList<>();

        for (Employee tempEmployee: employees) {

            List<TaskResponse> tasks = taskRepo.findTasksAssignedToById(tempEmployee.getId());
            EmployeeResponse employee = new EmployeeResponse(
                    tempEmployee.getId(),
                    tempEmployee.getUsername(),
                    tempEmployee.getAuthority(),
                    tempEmployee.getFirstName(),
                    tempEmployee.getLastName(),
                    tempEmployee.getEmail(),
                    tasks
            );
            processedEmployees.add(employee);
        }

        return processedEmployees;

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
                employee.getEmail(),
                tasks
        );

    }

}
