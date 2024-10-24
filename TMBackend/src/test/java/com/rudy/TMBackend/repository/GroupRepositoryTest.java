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
public class GroupRepositoryTest {
    
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() {
        groupRepository.deleteAll();
    }

    @Test
    public void testFindByName() {
        Group group = new Group();
        group.setName("testgroup");
        group = groupRepository.save(group);

        Group foundGroup = groupRepository.findByName("testgroup");
        assertEquals(group, foundGroup);
    }

    @Test
    public void testFindById() {
        Group group = new Group();
        group.setName("testgroup");
        group = groupRepository.save(group);

        Group foundGroup = groupRepository.findById(group.getId()).orElse(null);
        assertEquals(group, foundGroup);
    }

    @Test
    public void testFindByUsersContaining() {
        User user = new User();
        user.setName("testuser");
        user.setEmail("test@example.com");
        user = userRepository.save(user);

        Group group = new Group();
        group.setName("testgroup");
        group.setUsers(new HashSet<>(Arrays.asList(user)));
        group = groupRepository.save(group);

        Group group2 = new Group();
        group2.setName("testgroup2");
        group2.setUsers(new HashSet<>(Arrays.asList(user)));
        group2 = groupRepository.save(group2);

        List<Group> groups = groupRepository.findByUsersContaining(user);
        assertEquals(2, groups.size());
    }
}
