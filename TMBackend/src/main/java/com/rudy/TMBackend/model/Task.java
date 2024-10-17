package com.rudy.TMBackend.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fields
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Temporal(TemporalType.DATE)
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
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public User getOwnerUser() {
        return ownerUser;
    }
    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }
    public Group getOwnerGroup() {
        return ownerGroup;
    }
    public void setOwnerGroup(Group ownerGroup) {
        this.ownerGroup = ownerGroup;
    }
    public Set<User> getAssignedUsers() {
        return assignedUsers;
    }
    public void setAssignedUsers(Set<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }
}

