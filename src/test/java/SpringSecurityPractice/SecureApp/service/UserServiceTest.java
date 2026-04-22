package SpringSecurityPractice.SecureApp.service;

import SpringSecurityPractice.SecureApp.entity.Employee;
import SpringSecurityPractice.SecureApp.entity.requestEntity.RegisterRequest;
import SpringSecurityPractice.SecureApp.errorhandle.exceptions.EmailExistsException;
import SpringSecurityPractice.SecureApp.errorhandle.exceptions.UsernameExistsException;
import SpringSecurityPractice.SecureApp.repo.EmployeeRepo;
import SpringSecurityPractice.SecureApp.repo.TaskRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // tells JUnit to use Mockito
public class UserServiceTest {

    @Mock
    private EmployeeRepo userRepo;

    @Mock
    private TaskRepo taskRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeService userService;    // real UserService with fakes injected

    @Test
    void registerUser_shouldThrowDuplicateUsernameException_whenUsernameExists() {

        // ARRANGE - set up the scenario
        RegisterRequest request = new RegisterRequest();
        request.setUsername("jdoe");
        request.setEmail("jdoe@example.com");
        request.setPassword("password");
        request.setFirstName("John");
        request.setLastName("Doe");

        when(userRepo.existsByUsername("jdoe")).thenReturn(true);

        assertThrows(UsernameExistsException.class, () -> {
            userService.registerUser(request);
        });

        verify(userRepo, times(1)).existsByUsername("jdoe");

        verify(userRepo, never()).save(any());
    }

    @Test
    void registerUser_shouldThrowEmailExistsException_whenEmailExists() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("jdoe");
        request.setEmail("jdoe@example.com");
        request.setPassword("password");
        request.setFirstName("John");
        request.setLastName("Doe");

        when(userRepo.existsByEmail("jdoe@example.com")).thenReturn(true);

        assertThrows(EmailExistsException.class, () -> {
            userService.registerUser(request);
        });

        verify(userRepo, never()).save(any());
    }

    @Test
    void registerUser_shouldSaveEmployee_whenValidRequest() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("jdoe");
        request.setEmail("jdoe@example.com");
        request.setPassword("password");
        request.setFirstName("John");
        request.setLastName("Doe");

        when(userRepo.existsByEmail("jdoe@example.com")).thenReturn(false);
        when(userRepo.existsByUsername("jdoe")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("hashedpassword");

        userService.registerUser(request);

        verify(userRepo, times(1)).save(any(Employee.class));
    }

    @Test
    void findByUsername_shouldReturnEmployee_whenEmployeeExists() {
        Employee employee = new Employee();
        employee.setUsername("jdoe");
        employee.setEmail("jdoe@example.com");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setAuthority("USER");

        when(userRepo.findByUsername("jdoe")).thenReturn(Optional.of(employee));

        Employee result = userService.findByUsername("jdoe");


        assertNotNull(result);
        assertEquals("jdoe", result.getUsername());
        verify(userRepo, times(1)).findByUsername("jdoe");
    }

    @Test
    void findByUsername_shouldThrowException_whenEmployeeNotFound() {

        when(userRepo.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.findByUsername("unknown");
        });

        verify(userRepo, times(1)).findByUsername("unknown");
    }
}