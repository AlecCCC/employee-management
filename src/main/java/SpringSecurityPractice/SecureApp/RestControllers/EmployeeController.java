package SpringSecurityPractice.SecureApp.RestControllers;

import SpringSecurityPractice.SecureApp.entity.Employee;
import SpringSecurityPractice.SecureApp.entity.responseEntity.EmployeeResponse;
import SpringSecurityPractice.SecureApp.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.domain.Page;
@RestController
@RequestMapping("/security-practice")

public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<Page<EmployeeResponse>> getEmployees(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(value = "authority", required = false) String authority) {

        return ResponseEntity.ok(employeeService.findAllFiltered(authority, page, size));
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
