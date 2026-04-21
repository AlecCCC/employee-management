package SpringSecurityPractice.SecureApp.specifications;

import SpringSecurityPractice.SecureApp.entity.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    public static Specification<Task> byStatus(String status) {
        if (status == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(
                builder.upper(root.get("status")), status.toUpperCase()
        );
    }

    public static Specification<Task> byAssignedToUsername(String assigned_to) {
        if (assigned_to == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get("assignedTo").get("username"), assigned_to);
    }

    public static Specification<Task> byAssignedByUsername(String assigned_by) {
        if (assigned_by == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(
                builder.upper(root.get("assignedBy").get("username")), assigned_by.toUpperCase()
        );

    }

}
