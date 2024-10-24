package com.rudy.TMBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "tm_groups")
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the task", example = "1")
    private Long id;

    // Fields
    @Schema(description = "Name of the group", example = "Project Team")
    private String name;

    // Many-to-Many with User
    @ManyToMany
    @JoinTable(
        name = "user_group",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    // One-to-Many with Task (for tasks owned by the group)
    @OneToMany(mappedBy = "ownerGroup")
    private Set<Task> tasks = new HashSet<>();

    // Constructors
    public Group() {
    }
    public Group(String name) {
        this.name = name;
    }
    public Group(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }
    // Getters and Setters
    // public Long getId() {
    //     return id;
    // }
    // public void setId(Long id) {
    //     this.id = id;
    // }
    // public String getName() {
    //     return name;
    // }
    // public void setName(String name) {
    //     this.name = name;
    // }
    // public Set<User> getUsers() {
    //     return users;
    // }
    // public void setUsers(Set<User> users) {
    //     this.users = users;
    // }
    // public Set<Task> getTasks() {
    //     return tasks;
    // }
    // public void setTasks(Set<Task> tasks) {
    //     this.tasks = tasks;
    // }
}
