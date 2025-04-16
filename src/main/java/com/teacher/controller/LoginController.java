package com.teacher.controller;

import com.teacher.model.LoginRequest;
import com.teacher.model.User;
import com.teacher.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // allow frontend access
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Fetch the user from the database
        User user = userRepo.findByUsername(username);

        // Check if the user exists and password matches
        if (user != null && user.getPassword().equals(password)) {
            return ResponseEntity.ok(Map.of(
            	"id", user.getId(),
                "role", user.getRole(),
                "name", user.getName()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
