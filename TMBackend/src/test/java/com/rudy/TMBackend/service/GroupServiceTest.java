package com.rudy.TMBackend.service;

import com.rudy.TMBackend.model.*;
import com.rudy.TMBackend.repository.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {
    private Group group1;
    private Group group2;
    private User user1;
    private User user2;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    public void setUp() {
        user1 = new User("Alice", "user1email", "password1");
        user2 = new User("Bob", "user2email", "password2");

        group1 = new Group("Group 1", new HashSet<>(Arrays.asList(user1)));
        group2 = new Group("Group 2", new HashSet<>(Arrays.asList(user2)));
    }

    @Test
    public void testCreateGroup() {
        when(groupRepository.save(any(Group.class))).thenReturn(group1);
        when (userRepository.findById(1L)).thenReturn(Optional.of(user1));
        Group savedGroup = groupService.createGroup("Group 1", 1L);
        assertEquals("Group 1", savedGroup.getName());
        assertEquals(user1, savedGroup.getUsers().iterator().next());
    }

    @Test
    public void testGetAllGroups() {
        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        when(groupRepository.findAll()).thenReturn(groups);
        List<Group> allGroups = groupService.getAllGroups();
        assertEquals(groups, allGroups);
    }

    @Test
    public void testGetGroupById() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group1));
        Group group = groupService.getGroupById(1L);
        assertEquals(group1, group);
    }

    @Test
    public void testAddUserToGroup() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group1));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        groupService.addUserToGroup(1L, 1L);

        assertTrue(group1.getUsers().contains(user1));
        verify(groupRepository, times(1)).save(group1);
    }

    @Test
    public void testRemoveUserFromGroup() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group1));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        groupService.removeUserFromGroup(1L, 1L);
        assertTrue(!group1.getUsers().contains(user1));
        verify(groupRepository, times(1)).save(group1);
    }

    @Test
    public void testGetGroupsForUser() {
        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        group1.setUsers(new HashSet<>(Arrays.asList(user1)));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(groupRepository.findByUsersContaining(user1)).thenReturn(groups);

        List<Group> userGroups = groupService.getGroupsForUser(1L);
        assertEquals(groups, userGroups);
    }
}
