package SpringSecurityPractice.SecureApp.service;

import SpringSecurityPractice.SecureApp.entity.Employee;
import SpringSecurityPractice.SecureApp.entity.Task;
import SpringSecurityPractice.SecureApp.entity.requestEntity.RegisterRequest;
import SpringSecurityPractice.SecureApp.entity.responseEntity.EmployeeResponse;
import SpringSecurityPractice.SecureApp.entity.responseEntity.TaskResponse;
import SpringSecurityPractice.SecureApp.errorhandle.exceptions.EmailExistsException;
import SpringSecurityPractice.SecureApp.errorhandle.exceptions.EmployeeNotFoundException;
import SpringSecurityPractice.SecureApp.errorhandle.exceptions.UsernameExistsException;
import SpringSecurityPractice.SecureApp.repo.EmployeeRepo;
import SpringSecurityPractice.SecureApp.repo.TaskRepo;
import SpringSecurityPractice.SecureApp.specifications.EmployeeSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
                .orElseThrow(() -> new EmployeeNotFoundException(username));
    }

    public Employee findById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id.toString()));
    }

    public void registerUser(RegisterRequest request) {

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new EmailExistsException(request.getEmail());
        }

        if (userRepo.existsByUsername(request.getUsername())) {
            throw new UsernameExistsException(request.getUsername());
        }


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

        Employee employee = userRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

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

public Page<EmployeeResponse> findAllFiltered(String authority, int page, int size) {

    Specification<Employee> specification = (root, query, builder) -> null;

    if (authority != null && !authority.isBlank()) {
        specification = specification.and(EmployeeSpecification.byAuthority(authority));
    }

    Page<Employee> employees = userRepo.findAll(specification, PageRequest.of(page, size));

    return employees.map(employee -> new EmployeeResponse(
            employee.getId(),
            employee.getUsername(),
            employee.getAuthority(),
            employee.getFirstName(),
            employee.getLastName(),
            employee.getEmail(),
            taskRepo.findTasksAssignedToById(employee.getId())
    ));
}

}
