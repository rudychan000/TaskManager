package com.rudy.TMBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rudy.TMBackend.model.*;
import com.rudy.TMBackend.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;

import com.rudy.TMBackend.security.UserPrincipal;
import java.util.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    // Create a private task
    @Operation(summary = "Create a private task")
    @PostMapping("/private")
    public ResponseEntity<?> createPrivateTask(
            @RequestBody Task task,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Task createdTask = taskService.createPrivateTask(task, userPrincipal.getId());
        return ResponseEntity.ok(createdTask);
    }

    // Create a public task
    @PostMapping("/public/{groupId}")
    @Operation(summary = "Create a public task")
    public ResponseEntity<?> createPublicTask(
            @PathVariable Long groupId,
            @RequestBody Task task,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        // Optional: Verify that the user is a member of the group
        Task createdTask = taskService.createPublicTask(task, groupId);
        return ResponseEntity.ok(createdTask);
    }

    // Assign users to a public task
    @Operation(summary = "Assign users to a public task")
    @PostMapping("/{taskId}/assign")
    public ResponseEntity<?> assignUsersToTask(
            @PathVariable Long taskId,
            @RequestBody Set<Long> userIds,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Task updatedTask = taskService.assignUsersToTask(taskId, userIds, userPrincipal.getId());
        return ResponseEntity.ok(updatedTask);
    }

    // Get tasks for the authenticated user
    @Operation(summary = "Get tasks for the authenticated user")
    @GetMapping("/my-tasks")
    public ResponseEntity<?> getTasksForUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<Task> tasks = taskService.getTasksForUser(userPrincipal.getId());
        return ResponseEntity.ok(tasks);
    }
}
