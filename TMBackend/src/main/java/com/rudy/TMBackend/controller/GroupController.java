package com.rudy.TMBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rudy.TMBackend.model.*;
import com.rudy.TMBackend.service.GroupService;

import io.swagger.v3.oas.annotations.Operation;

import com.rudy.TMBackend.security.UserPrincipal;
import java.util.*;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
     @Autowired
    private GroupService groupService;

    // Create a new group
    @PostMapping("create")
    @Operation(summary = "Create a new group")
    public ResponseEntity<?> createGroup(
            @RequestBody Map<String, String> request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        String groupName = request.get("name");
        Group group = groupService.createGroup(groupName, userPrincipal.getId());
        return ResponseEntity.ok(group);
    }

    // Add a user to a group
    @PostMapping("/{groupId}/add-user/{userId}")
    @Operation(summary = "Add a user to a group")
    public ResponseEntity<?> addUserToGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Group updatedGroup = groupService.addUserToGroup(groupId, userId, userPrincipal.getId());
        return ResponseEntity.ok(updatedGroup);
    }

    // Remove a user from a group
    @PostMapping("/{groupId}/remove-user/{userId}")
    @Operation(summary = "Remove a user from a group")
    public ResponseEntity<?> removeUserFromGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Group updatedGroup = groupService.removeUserFromGroup(groupId, userId, userPrincipal.getId());
        return ResponseEntity.ok(updatedGroup);
    }

    // Get groups for the authenticated user
    @GetMapping("/my-groups")
    @Operation(summary = "Get groups for the authenticated user")
    public ResponseEntity<?> getGroupsForUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<Group> groups = groupService.getGroupsForUser(userPrincipal.getId());
        return ResponseEntity.ok(groups);
    }
}
