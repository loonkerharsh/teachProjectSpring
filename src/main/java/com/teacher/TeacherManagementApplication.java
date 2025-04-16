package com.teacher;

import com.teacher.model.User;
import com.teacher.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeacherManagementApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepo;

    public static void main(String[] args) {
        SpringApplication.run(TeacherManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if the database already has users
    	if (userRepo.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setRole("admin");
            admin.setName("Admin User");
            userRepo.save(admin);
        }

        // Create teacher user if not exists
        if (userRepo.findByUsername("teacher") == null) {
            User teacher = new User();
            teacher.setUsername("teacher");
            teacher.setPassword("teacher");
            teacher.setRole("teacher");
            teacher.setName("John Doe");
            userRepo.save(teacher);
        }
    }

}
