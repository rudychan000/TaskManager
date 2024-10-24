package com.rudy.TMBackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the task", example = "1")
    private Long id;

    // Fields
    @Schema(description = "Title of the task", example = "Complete Project")
    private String title;
    @Schema(description = "Description of the task", example = "Finish the task management project")
    private String description;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status of the task", example = "PENDING")
    private TaskStatus status;

    @Temporal(TemporalType.DATE)
    @Schema(description = "Due date of the task", example = "2024.12.31")
    private Date dueDate;

    // Many-to-One with User (for private tasks)
    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User ownerUser;

    // Many-to-One with Group (for group tasks)
    @ManyToOne
    @JoinColumn(name = "owner_group_id")
    private Group ownerGroup;

    // Many-to-Many with User (assigned users)
    @ManyToMany
    @JoinTable(
        name = "task_user",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> assignedUsers = new HashSet<>();


    // Validation
    @PrePersist
    @PreUpdate
    public void validateOwnership() {
        if ((ownerUser == null && ownerGroup == null) || (ownerUser != null && ownerGroup != null)) {
            throw new IllegalStateException("A task must have either an owner user or an owner group, but not both.");
        }
    }

    // Constructors
    public Task() {
    }
    public Task(String title, String description, TaskStatus status, Date dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    // public Long getId() {
    //     return id;
    // }
    // public void setId(Long id) {
    //     this.id = id;
    // }
    // public String getTitle() {
    //     return title;
    // }
    // public void setTitle(String title) {
    //     this.title = title;
    // }
    // public String getDescription() {
    //     return description;
    // }
    // public void setDescription(String description) {
    //     this.description = description;
    // }
    // public TaskStatus getStatus() {
    //     return status;
    // }
    // public void setStatus(TaskStatus status) {
    //     this.status = status;
    // }
    // public Date getDueDate() {
    //     return dueDate;
    // }
    // public void setDueDate(Date dueDate) {
    //     this.dueDate = dueDate;
    // }
    // public User getOwnerUser() {
    //     return ownerUser;
    // }
    // public void setOwnerUser(User ownerUser) {
    //     this.ownerUser = ownerUser;
    // }
    // public Group getOwnerGroup() {
    //     return ownerGroup;
    // }
    // public void setOwnerGroup(Group ownerGroup) {
    //     this.ownerGroup = ownerGroup;
    // }
    // public Set<User> getAssignedUsers() {
    //     return assignedUsers;
    // }
    // public void setAssignedUsers(Set<User> assignedUsers) {
    //     this.assignedUsers = assignedUsers;
    // }
}

