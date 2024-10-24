package com.rudy.TMBackend.service;

import com.rudy.TMBackend.model.*;
import com.rudy.TMBackend.repository.*;
import com.rudy.TMBackend.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new group
    public Group createGroup(String groupName, Long creatorId) {
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Group group = new Group();
        group.setName(groupName);
        group.getUsers().add(creator);

        return groupRepository.save(group);
    }

    // Get all groups
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    // Get group by id
    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    }



    // Add user to group
    public Group addUserToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));


        User userToAdd = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        group.getUsers().add(userToAdd);
        return groupRepository.save(group);
    }
    // Remove user from group
    // TODO remove user assignment from tasks
    public Group removeUserFromGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        User userToRemove = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        group.getUsers().remove(userToRemove);
        return groupRepository.save(group);
    }

    // Get groups for user
    public List<Group> getGroupsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return groupRepository.findByUsersContaining(user);
    }
}
