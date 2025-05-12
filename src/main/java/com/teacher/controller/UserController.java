package com.teacher.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.teacher.model.User;
import com.teacher.repository.UserRepository;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}/profile")
    public ResponseEntity<?> getTeacherProfile(@PathVariable Long id) {
        try {
            User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Create a response object without password
            Map<String, Object> profile = new HashMap<>();
            profile.put("id", user.getId());
            profile.put("username", user.getUsername());
            profile.put("name", user.getName());
            profile.put("role", user.getRole());
            
            // Add semesters if needed
            if (user.getSemesters() != null) {
                profile.put("semesters", user.getSemesters().stream()
                    .map(s -> Map.of("id", s.getId(), "name", s.getName()))
                    .collect(Collectors.toList()));
            }
            
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error fetching profile: " + e.getMessage());
        }
    }
}