package com.example.demo.model;


import com.sun.istack.NotNull;

import javax.persistence.*;

@Table
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @Column(columnDefinition = "boolean default false")
    private Boolean complete = false;

    public Task() {
    }

    public Task(String name) {
        this.name = name;
    }

    public Task(String name, Boolean complete) {
        this.name = name;
        this.complete = complete;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}
