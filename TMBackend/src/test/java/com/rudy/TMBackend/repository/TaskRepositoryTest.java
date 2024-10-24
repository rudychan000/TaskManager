package com.rudy.TMBackend.repository;

import com.rudy.TMBackend.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @AfterEach
    public void tearDown() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    public void testFindByOwnerUser() {
        User user = new User();
        user.setName("testuser");
        user.setEmail("testuser@example.com");
        user = userRepository.save(user);

        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setOwnerUser(user);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setOwnerUser(user);
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findByOwnerUser(user);
        assertEquals(2, tasks.size());
    }

    @Test
    public void testFindByOwnerGroup() {
        Group group = new Group();
        group.setName("testgroup");
        group = groupRepository.save(group);

        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setOwnerGroup(group);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setOwnerGroup(group);
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findByOwnerGroup(group);
        assertEquals(2, tasks.size());
    }

    @Test
    public void testFindByAssignedUsersContains() {
        User user = new User();
        user.setName("testuser");
        user.setEmail("testuser@example.com");
        user = userRepository.save(user);
        
        Group group = new Group();
        group.setName("testgroup");
        group = groupRepository.save(group);
        
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setOwnerGroup(group);
        task1.setAssignedUsers(new HashSet<>(Arrays.asList(user)));
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setOwnerGroup(group);
        task2.setAssignedUsers(new HashSet<>(Arrays.asList(user)));
        taskRepository.save(task2);

        // Task 3 is a private task, should not be returned
        Task task3 = new Task();
        task3.setTitle("Task 3");
        task3.setOwnerUser(user);
        taskRepository.save(task3);

        List<Task> tasks = taskRepository.findByAssignedUsersContains(user);
        assertEquals(2, tasks.size());
        assertTrue(!tasks.contains(task3));
    }

    @Test
    public void testFindByOwnerGroupAndAssignedUsersContains() {
        User user = new User();
        user.setName("testuser");
        user.setEmail("testuser@example.com");
        user = userRepository.save(user);
        
        Group group1 = new Group();
        group1.setName("testgroup1");
        group1 = groupRepository.save(group1);

        Group group2 = new Group();
        group2.setName("testgroup2");
        group2 = groupRepository.save(group2);
    
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setOwnerGroup(group1);
        task1.setAssignedUsers(new HashSet<>(Arrays.asList(user)));
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setOwnerGroup(group2);
        task2.setAssignedUsers(new HashSet<>(Arrays.asList(user)));
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findByOwnerGroupAndAssignedUsersContains(group1,user);
        assertEquals(1, tasks.size());
    }
}

