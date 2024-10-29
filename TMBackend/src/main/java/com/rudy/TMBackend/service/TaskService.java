package com.rudy.TMBackend.service;

import com.rudy.TMBackend.model.*;
import com.rudy.TMBackend.repository.*;
import com.rudy.TMBackend.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    // Create a private task
    public Task createPrivateTask(Task task, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        task.setOwnerUser(user);
        task.setOwnerGroup(null);
        return taskRepository.save(task);
    }

    // Create a public task
    public Task createPublicTask(Task task, Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        task.setOwnerGroup(group);
        task.setOwnerUser(null);

        return taskRepository.save(task);
    }

     // Assign users to a public task
     public Task assignUsersToTask(Long taskId, Set<Long> userIds, Long currentUserId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (task.getOwnerGroup() == null) {
            throw new IllegalArgumentException("Cannot assign users to a private task.");
        }

        // Check if current user is a member of the group
        Group group = task.getOwnerGroup();
        boolean isMember = group.getUsers().stream()
                .anyMatch(u -> u.getId().equals(currentUserId));

        if (!isMember) {
            throw new AccessDeniedException("You are not a member of this group.");
        }

        // Get users and ensure they are members of the group
        Set<User> usersToAssign = new HashSet<>();
        for (Long userId : userIds) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

            boolean userIsMember = group.getUsers().contains(user);
            if (!userIsMember) {
                throw new IllegalArgumentException("User with ID " + userId + " is not a member of the group.");
            }

            usersToAssign.add(user);
        }

        task.setAssignedUsers(usersToAssign);
        return taskRepository.save(task);
    }

    // Get a task by id
    public Task getTaskById(Long taskId){
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    // Get tasks for a user (both private and assigned public tasks)
    public List<Task> getTasksForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Task> privateTasks = taskRepository.findByOwnerUser(user);
        List<Task> assignedTasks = taskRepository.findByAssignedUsersContains(user);

        Set<Task> allTasks = new HashSet<>();
        allTasks.addAll(privateTasks);
        allTasks.addAll(assignedTasks);

        return new ArrayList<>(allTasks);
    }

    // Get private tasks for a user
    public List<Task> getPrivateTasksForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return taskRepository.findByOwnerUser(user);
    }

    // Get public tasks for a user
    public List<Task> getPublicTasksForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return taskRepository.findByAssignedUsersContains(user);
    }

    // Get tasks for a group (public tasks)
    public List<Task> getTasksForGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        return taskRepository.findByOwnerGroup(group);
    }

    // Get tasks for a user within a group (public tasks)
    public List<Task> getTasksForUserInGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return taskRepository.findByOwnerGroupAndAssignedUsersContains(group, user);
    }

    // Delete a task
    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (task.getOwnerUser() != null) {
            if (!task.getOwnerUser().getId().equals(userId)) {
                throw new AccessDeniedException("You do not have permission to delete this task.");
            }
        } else {
            Group group = task.getOwnerGroup();
            boolean isMember = group.getUsers().stream()
                    .anyMatch(u -> u.getId().equals(userId));

            if (!isMember) {
                throw new AccessDeniedException("You do not have permission to delete this task.");
            }
        }

        taskRepository.delete(task);
    }
    
}
