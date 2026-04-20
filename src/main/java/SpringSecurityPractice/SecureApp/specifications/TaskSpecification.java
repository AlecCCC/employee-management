package SpringSecurityPractice.SecureApp.specifications;

import SpringSecurityPractice.SecureApp.entity.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    public static Specification<Task> byStatus(String status) {

        if (status == null) {
            return null;
        }

        return (root, query, builder) -> builder.equal(root.get("status"), status);

    }

    public static Specification<Task> byUsername(String username) {
        if (username == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get("assigned_to").get("username"), username);
    }

}
