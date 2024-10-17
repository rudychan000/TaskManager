package com.rudy.TMBackend.repository;

import com.rudy.TMBackend.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
    // Find group by name
    Group findByName(String name);
    // Find group by id
    Optional<Group> findById(Long id);
}
