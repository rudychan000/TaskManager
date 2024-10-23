package com.rudy.TMBackend.model;

import jakarta.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Test {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

	private String name;

    private String content;

    // Constructors
    public Test() {
    }
    public Test(String name, String content) {
        this.name = name;
        this.content = content;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }
}
