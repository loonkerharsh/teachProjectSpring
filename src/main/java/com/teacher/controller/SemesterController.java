// SemesterController.java
package com.teacher.controller;

import com.teacher.model.Semester;
import com.teacher.model.User;
import com.teacher.repository.UserRepository;
import com.teacher.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/semesters")
public class SemesterController {
    private final SemesterService semesterService;
    private final UserRepository userRepository;

    @Autowired
    public SemesterController(SemesterService semesterService, UserRepository userRepository) {
        this.semesterService = semesterService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Set<Semester> getSemestersForTeacher(@RequestParam Long teacherId) {
        User user = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + teacherId));
        return semesterService.getSemestersByUser(user);
    }
}