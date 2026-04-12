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
}