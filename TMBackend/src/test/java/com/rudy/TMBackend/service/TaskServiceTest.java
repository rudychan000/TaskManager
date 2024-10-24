package com.rudy.TMBackend.service;

import com.rudy.TMBackend.model.*;
import com.rudy.TMBackend.repository.*;
import com.rudy.TMBackend.exception.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private TaskService taskService;

    private User user;
    private User user2;
    private Task private_task;
    private Task public_task;
    private Group group;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create a user
        user = new User();
        user.setId(1L);
        user.setName("testuser");
        user.setEmail("testuser@example.com");

        // Create a user2
        user2 = new User();
        user2.setId(2L);
        user2.setName("testuser2");
        user2.setEmail("testuser2@example.com");

        // Create a group
        group = new Group();
        group.setId(1L);
        group.setName("Test Group");

        // Create a private task
        private_task = new Task();
        private_task.setId(1L);
        private_task.setTitle("Test Private Task");
        private_task.setDescription("Private Task description");
        private_task.setStatus(TaskStatus.PENDING);
        private_task.setOwnerUser(user);

        // Create a public task
        public_task = new Task();
        public_task.setId(2L);
        public_task.setTitle("Test Public Task");
        public_task.setDescription("Public Task description");
        public_task.setStatus(TaskStatus.PENDING);
        public_task.setOwnerGroup(group);

    }

    @Test
    public void testCreatePrivateTask() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(private_task);

        Task createdTask = taskService.createPrivateTask(private_task, user.getId());

        assertNotNull(createdTask);
        assertEquals(private_task.getTitle(), createdTask.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testCreatePrivateTask_UserNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.createPrivateTask(private_task, user.getId());
        });
    }

    @Test
    public void testCreatePublicTask() {
        when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
        when(taskRepository.save(any(Task.class))).thenReturn(public_task);

        Task createdTask = taskService.createPublicTask(public_task, group.getId());

        assertNotNull(createdTask);
        assertEquals(public_task.getTitle(), createdTask.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testCreatePublicTask_GroupNotFound() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.createPublicTask(public_task, 1L);
        });
    }

    @Test
    public void testAssignUsersToTask() {
        Set<Long> userIds = new HashSet<>();
        userIds.add(user.getId());
        userIds.add(user2.getId());

        group.setUsers(new HashSet<>(Arrays.asList(user, user2)));

        when(taskRepository.findById(public_task.getId())).thenReturn(Optional.of(public_task));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findById(user2.getId())).thenReturn(Optional.of(user2));
        when(taskRepository.save(any(Task.class))).thenReturn(public_task);

        Task assignedTask = taskService.assignUsersToTask(public_task.getId(), userIds, user.getId());
        assertNotNull(assignedTask);
        assertEquals(assignedTask.getAssignedUsers().size(), 2);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testAssignUsersToTask_TaskNotFound() {
        Set<Long> userIds = new HashSet<>();
        userIds.add(user.getId());

        when(taskRepository.findById(public_task.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.assignUsersToTask(public_task.getId(), userIds, user.getId());
        });
    }

    @Test
    public void testAssignUsersToTask_CurrentUserNotMemberOfGroup() {
        Set<Long> userIds = new HashSet<>();
        userIds.add(user.getId());

        when(taskRepository.findById(public_task.getId())).thenReturn(Optional.of(public_task));
        assertThrows(AccessDeniedException.class, () -> {
            taskService.assignUsersToTask(public_task.getId(), userIds, user.getId());
        });
    }

    @Test
    public void testAssignUsersToTask_AssignedUserNotFound() {
        Set<Long> userIds = new HashSet<>();
        userIds.add(user.getId());
        userIds.add(user2.getId());

        group.setUsers(new HashSet<>(Arrays.asList(user)));

        when(taskRepository.findById(public_task.getId())).thenReturn(Optional.of(public_task));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        // user2 is not found
        when(userRepository.findById(user2.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.assignUsersToTask(public_task.getId(), userIds, user.getId());
        });
    }

    @Test  
    public void testAssignUsersToTask_AssignedUserNotMemberOfGroup() {
        Set<Long> userIds = new HashSet<>();
        userIds.add(user.getId());
        userIds.add(user2.getId());
        // user2 is not a member of the group
        group.setUsers(new HashSet<>(Arrays.asList(user)));

        when(taskRepository.findById(public_task.getId())).thenReturn(Optional.of(public_task));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findById(user2.getId())).thenReturn(Optional.of(user2));
        assertThrows(IllegalArgumentException.class, () -> {
            taskService.assignUsersToTask(public_task.getId(), userIds, user.getId());
        });
    }

    @Test
    public void testAssignUsersToTask_PrivateTask() {
        Set<Long> userIds = new HashSet<>();
        userIds.add(user.getId());

        when(taskRepository.findById(private_task.getId())).thenReturn(Optional.of(private_task));

        assertThrows(IllegalArgumentException.class, () -> {
            taskService.assignUsersToTask(private_task.getId(), userIds, user.getId());
        });
    }

    @Test
    public void testGetTasksForUser(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(taskRepository.findByOwnerUser(user)).thenReturn(new ArrayList<>(Arrays.asList(private_task)));
        List<Task> tasks = taskService.getTasksForUser(user.getId());
        assertNotNull(tasks);
        assertEquals(tasks.size(), 1);
        assertEquals(tasks.get(0).getTitle(), private_task.getTitle());
    }

    @Test
    public void testGetTasksForUser_UserNotFound(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getTasksForUser(user.getId());
        });
    }

    @Test
    public void testGetTasksForGroup(){
        when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
        when(taskRepository.findByOwnerGroup(group)).thenReturn(new ArrayList<>(Arrays.asList(public_task)));
        List<Task> tasks = taskService.getTasksForGroup(group.getId());
        assertNotNull(tasks);
        assertEquals(tasks.size(), 1);
        assertEquals(tasks.get(0).getTitle(), public_task.getTitle());
    }

    @Test
    public void testGetTasksForGroup_GroupNotFound(){
        when(groupRepository.findById(group.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getTasksForGroup(group.getId());
        });
    }

    @Test
    public void testGetTasksForUserInGroup(){
        when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(taskRepository.findByOwnerGroupAndAssignedUsersContains(group, user)).thenReturn(new ArrayList<>(Arrays.asList(public_task)));
        List<Task> tasks = taskService.getTasksForUserInGroup(group.getId(), user.getId());
        assertNotNull(tasks);
        assertEquals(tasks.size(), 1);
        assertEquals(tasks.get(0).getTitle(), public_task.getTitle());
    }

    @Test
    public void testGetTasksForUserInGroup_GroupNotFound(){
        when(groupRepository.findById(group.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getTasksForUserInGroup(group.getId(), user.getId());
        });
    }

    @Test
    public void testGetTasksForUserInGroup_UserNotFound(){
        when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getTasksForUserInGroup(group.getId(), user.getId());
        });
    }

    @Test
    public void testDeletePrivateTask(){
        when(taskRepository.findById(private_task.getId())).thenReturn(Optional.of(private_task));
        taskService.deleteTask(private_task.getId(), user.getId());
        verify(taskRepository, times(1)).delete(private_task);
    }

    @Test
    public void testDeletePrivateTask_TaskNotFound(){
        when(taskRepository.findById(private_task.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.deleteTask(private_task.getId(), user.getId());
        });
    }

    @Test
    public void testDeletePrivateTask_AccessDenied(){
        when(taskRepository.findById(private_task.getId())).thenReturn(Optional.of(private_task));
        assertThrows(AccessDeniedException.class, () -> {
            taskService.deleteTask(private_task.getId(), user2.getId());
        });
    }

    @Test
    public void testDeletePublicTask(){
        when(taskRepository.findById(public_task.getId())).thenReturn(Optional.of(public_task));
        group.setUsers(new HashSet<>(Arrays.asList(user)));
        taskService.deleteTask(public_task.getId(), user.getId());
        verify(taskRepository, times(1)).delete(public_task);
    }

    @Test
    public void testDeletePublicTask_TaskNotFound(){
        when(taskRepository.findById(public_task.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.deleteTask(public_task.getId(), user.getId());
        });
    }

    @Test
    public void testDeletePublicTask_AccessDenied(){
        when(taskRepository.findById(public_task.getId())).thenReturn(Optional.of(public_task));
        assertThrows(AccessDeniedException.class, () -> {
            taskService.deleteTask(public_task.getId(), user2.getId());
        });
    }
}
