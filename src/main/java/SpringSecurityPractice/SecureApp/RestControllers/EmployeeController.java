package SpringSecurityPractice.SecureApp.RestControllers;

import SpringSecurityPractice.SecureApp.entity.Employee;
import SpringSecurityPractice.SecureApp.entity.responseEntity.EmployeeResponse;
import SpringSecurityPractice.SecureApp.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/security-practice")

public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeResponse>> getEmployees() {

        return ResponseEntity.ok(employeeService.getEmployees());

    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        Employee loggedInEmployee = employeeService.findByUsername(userDetails.getUsername());
        boolean isAdmin = loggedInEmployee.getAuthority().equals("ADMIN");
        boolean isOwnData = loggedInEmployee.getId().equals(id);

        if (!isAdmin && !isOwnData) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(employeeService.getEmployeeWithTask(id));
    }


}
