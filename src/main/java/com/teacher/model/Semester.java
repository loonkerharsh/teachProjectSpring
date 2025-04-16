// Semester.java
package com.teacher.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "semesters")
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 
    private int id;
    private String name;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "semesters")
    private Set<User> teachers = new HashSet<>();

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<User> getTeachers() { return teachers; }
    public void setTeachers(Set<User> teachers) { this.teachers = teachers; }
}