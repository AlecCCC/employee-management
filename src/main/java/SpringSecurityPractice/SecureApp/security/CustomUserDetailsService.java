package SpringSecurityPractice.SecureApp.security;

import SpringSecurityPractice.SecureApp.entity.Employee;
import SpringSecurityPractice.SecureApp.repo.EmployeeRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

   private EmployeeRepo userRepo;

    public CustomUserDetailsService(EmployeeRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException ("User Not Found: " + username));

        return User
                .withUsername(employee.getUsername())
                .password(employee.getPassword())
                .authorities(employee.getAuthority())
                .build();
    }
}
