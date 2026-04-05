package SpringSecurityPractice.SecureApp.repo;

import SpringSecurityPractice.SecureApp.entity.Task;
import SpringSecurityPractice.SecureApp.entity.requestEntity.TaskRequest;
import SpringSecurityPractice.SecureApp.entity.responseEntity.TaskResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    @Query("SELECT new SpringSecurityPractice.SecureApp.entity.responseEntity.TaskResponse(" +
            "t.id, t.title, t.description, t.status, t.dueDate, " +
            "e1.username, e2.username) " +
            "FROM Task t " +
            "JOIN t.assigned_to e1 " +
            "JOIN t.assigned_by e2")
    List<TaskResponse> findAllTasksWithUsernames();

}
