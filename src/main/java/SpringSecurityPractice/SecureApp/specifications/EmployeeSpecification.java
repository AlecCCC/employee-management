package SpringSecurityPractice.SecureApp.specifications;


import SpringSecurityPractice.SecureApp.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Employee> byAuthority(String authority) {
        if (authority == null || authority.isBlank()) {
            return null;
        }
        return (root, query, builder) ->
                builder.equal(builder.upper(root.get("authority")), authority.toUpperCase());
    }

}
