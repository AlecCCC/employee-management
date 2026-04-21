package SpringSecurityPractice.SecureApp.repo;

import SpringSecurityPractice.SecureApp.entity.Task;
import SpringSecurityPractice.SecureApp.entity.responseEntity.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    @Query(value = "SELECT new SpringSecurityPractice.SecureApp.entity.responseEntity.TaskResponse(" +
            "t.id, t.title, t.description, t.status, t.dueDate, " +
            "e1.username, e2.username) " +
            "FROM Task t " +
            "JOIN t.assignedTo e1 " +
            "JOIN t.assignedBy e2",
            countQuery = "SELECT COUNT(t) FROM Task t")
    Page<TaskResponse> findAllTasksWithUsernames(Pageable pageable);


    @Query("SELECT new SpringSecurityPractice.SecureApp.entity.responseEntity.TaskResponse(" +
            "t.id, t.title, t.description, t.status, t.dueDate, " +
            "e1.username, e2.username) " +
            "FROM Task t " +
            "JOIN t.assignedTo e1 " +
            "JOIN t.assignedBy e2 " +
            "WHERE e1.id = :employeeId")
    List<TaskResponse> findTasksAssignedToById(@Param("employeeId") Long employeeId);

}
