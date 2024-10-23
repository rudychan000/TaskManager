package com.rudy.TMBackend.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the task", example = "1")
    private Long id;
    @Schema(description = "Name of the user", example = "Alice")
    private String name;
    @Schema(description = "Email of the user", example = "example@gmail.com")
    private String email;
    @Schema(description = "Password of the user", example = "encrypted_password")
    private String password;

    // Many-to-Many with Group
    @ManyToMany(mappedBy = "users")
    private Set<Group> groups = new HashSet<>();

    // Many-to-Many with Task (for assigned tasks)
    @ManyToMany(mappedBy = "assignedUsers")
    private Set<Task> assignedTasks = new HashSet<>();

    // One-to-Many with Task (for private tasks)
    @OneToMany(mappedBy = "ownerUser")
    private Set<Task> ownedTasks = new HashSet<>();

    // Constructors
    public User() {
    }
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<Group> getGroups() {
        return groups;
    }
    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
    public Set<Task> getAssignedTasks() {
        return assignedTasks;
    }
    public void setAssignedTasks(Set<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }
    public Set<Task> getOwnedTasks() {
        return ownedTasks;
    }
    public void setOwnedTasks(Set<Task> ownedTasks) {
        this.ownedTasks = ownedTasks;
    }
}
