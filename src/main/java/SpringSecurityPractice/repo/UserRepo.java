package SpringSecurityPractice.repo;

import SpringSecurityPractice.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepo extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUsername(String username);

}
