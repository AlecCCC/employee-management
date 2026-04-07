package SpringSecurityPractice.SecureApp.RestControllers;

import SpringSecurityPractice.SecureApp.entity.Employee;
import SpringSecurityPractice.SecureApp.entity.requestEntity.RegisterRequest;
import SpringSecurityPractice.SecureApp.security.Jwt.JwtUtil;
import SpringSecurityPractice.SecureApp.security.Jwt.dto.LoginRequest;
import SpringSecurityPractice.SecureApp.security.Jwt.dto.LoginResponse;
import SpringSecurityPractice.SecureApp.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security-practice")
public class AuthController {

    private final EmployeeService employeeService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(EmployeeService employeeService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.employeeService = employeeService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        employeeService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Employee employee = employeeService.findByUsername(request.getUsername());
        String token = jwtUtil.generateToken(request.getUsername());

        return ResponseEntity.ok(new LoginResponse(token, request.getUsername(), employee.getId(), employee.getAuthority()));

    }

}
