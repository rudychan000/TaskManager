package com.rudy.TMBackend.repository;

import com.rudy.TMBackend.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    // Private tasks owned by a user
    List<Task> findByOwnerUser(User ownerUser);

    // Public tasks owned by a group
    List<Task> findByOwnerGroup(Group ownerGroup);

    // Tasks assigned to a user(only public tasks)
    List<Task> findByAssignedUsersContains(User user);

    // Tasks assigned to a user within a group
    List<Task> findByOwnerGroupAndAssignedUsersContains(Group group, User user);
}
