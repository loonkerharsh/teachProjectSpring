package com.teacher.controller;

import com.teacher.model.Semester;
import com.teacher.model.Subject;
import com.teacher.model.User;
import com.teacher.repository.SemesterRepository;
import com.teacher.repository.SubjectRepository;
import com.teacher.repository.UserRepository; // Add this import
import com.teacher.service.SemesterService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/semesters")
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    @Autowired // Add this annotation
    private UserRepository userRepository; // Add this field
    @Autowired
    private SemesterRepository semesterRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping
    @Transactional
    public Set<Semester> getSemestersForTeacher(@RequestParam Long teacherId) {
        User user = userRepository.findById(teacherId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        return semesterService.getSemestersByUser(user);
    }
    
    @PostMapping
    public ResponseEntity<Subject> addSubject(@RequestBody Subject subjectRequest) {
        // Fetch the semester and teacher first
        Semester semester = semesterRepository.findById(subjectRequest.getSemesterId())
            .orElseThrow(() -> new RuntimeException("Semester not found"));
        
        User teacher = userRepository.findById(subjectRequest.getTeacherId())
            .orElseThrow(() -> new RuntimeException("Teacher not found"));

        // Create new subject
        Subject subject = new Subject();
        subject.setName(subjectRequest.getName());
        subject.setClassroom(subjectRequest.getClassroom());
        subject.setClassTime(subjectRequest.getClassTime());
        subject.setCompletionNote(subjectRequest.getCompletionNote());
        subject.setHomeworkNote(subjectRequest.getHomeworkNote());
        subject.setSemester(semester);
        subject.setTeacher(teacher);

        Subject savedSubject = subjectRepository.save(subject);
        return ResponseEntity.ok(savedSubject);
    }
}