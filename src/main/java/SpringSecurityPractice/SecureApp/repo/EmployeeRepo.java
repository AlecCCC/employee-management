package SpringSecurityPractice.SecureApp.repo;

import SpringSecurityPractice.SecureApp.entity.Employee;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    Optional<Employee> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
