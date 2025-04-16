package com.teacher.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "semesters")
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Match INT type in MySQL
    
    private String name;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "semesters")
    private Set<User> teachers;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<User> getTeachers() { return teachers; }
    public void setTeachers(Set<User> teachers) { this.teachers = teachers; }
}