package com.teacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.teacher.model.User;
import com.teacher.repository.SemesterRepository;
import com.teacher.repository.SubjectRepository;
import com.teacher.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminDashboardController {

    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTeachers", userRepository.countByRole("teacher"));
        stats.put("totalSubjects", subjectRepository.count());
        stats.put("totalSemesters", semesterRepository.count());
        return stats;
    }

    
    @GetMapping("/teachers")
    public List<User> getAllTeachers() {
        return userRepository.findAll()
                             .stream()
                             .filter(user -> "teacher".equals(user.getRole()))
                             .toList();
    }
    
 // Add a new teacher
    @PostMapping("/teachers/add")
    public String addTeacher(@RequestParam String username,
                             @RequestParam String name,
                             @RequestParam String password) {
        if (userRepository.existsByUsername(username)) 
        {
            return "Username already exists";
        }

        User teacher = new User();
        teacher.setUsername(username);
        teacher.setPassword(password);
        teacher.setName(name);
        teacher.setRole("teacher");

        userRepository.save(teacher);
        return "Teacher added successfully";
    }

    // Delete a teacher
    @PostMapping("/teachers/delete")
    public String deleteTeacher(@RequestParam Long id) 
    {
        if (!userRepository.existsById(id)) 
        {
            return "Teacher not found";
        }

        userRepository.deleteById(id);
        return "Teacher deleted successfully";
    }

}
