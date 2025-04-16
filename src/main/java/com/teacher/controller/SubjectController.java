// SubjectController.java
package com.teacher.controller;

import com.teacher.model.Semester;
import com.teacher.model.Subject;
import com.teacher.model.User;
import com.teacher.repository.SemesterRepository;
import com.teacher.repository.UserRepository;
import com.teacher.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
	
	@Autowired
    private SubjectService subjectService;

    
    
    @Autowired
    private SemesterRepository semesterRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Subject> getSubjectsForSemester(
            @RequestParam Integer semesterId,
            @RequestParam Long teacherId) {
        return subjectService.getSubjectsBySemesterAndTeacher(semesterId, teacherId);
    }

    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody Subject subjectRequest) {
        try {
            // Verify required IDs are present
            if (subjectRequest.getSemester() == null || subjectRequest.getSemester().getId() == null) {
                return ResponseEntity.badRequest().body("Semester ID is required");
            }
            if (subjectRequest.getTeacher() == null || subjectRequest.getTeacher().getId() == null) {
                return ResponseEntity.badRequest().body("Teacher ID is required");
            }

            // Fetch the full entities
            Semester semester = semesterRepository.findById(subjectRequest.getSemester().getId())
                .orElseThrow(() -> new RuntimeException("Semester not found"));
            
            User teacher = userRepository.findById(subjectRequest.getTeacher().getId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

            // Create new subject with relationships
            Subject subject = new Subject();
            subject.setName(subjectRequest.getName());
            subject.setClassroom(subjectRequest.getClassroom());
            subject.setClassTime(subjectRequest.getClassTime());
            subject.setCompletionNote(subjectRequest.getCompletionNote());
            subject.setHomeworkNote(subjectRequest.getHomeworkNote());
            subject.setSemester(semester);
            subject.setTeacher(teacher);

            Subject savedSubject = subjectService.saveSubject(subject);
            return ResponseEntity.ok(savedSubject);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating subject: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Subject updateSubject(@PathVariable Long id, @RequestBody Subject subjectDetails) {
        Subject subject = subjectService.getSubjectById(id);
        subject.setName(subjectDetails.getName());
        subject.setClassroom(subjectDetails.getClassroom());
        subject.setClassTime(subjectDetails.getClassTime());
        subject.setCompletionNote(subjectDetails.getCompletionNote());
        subject.setHomeworkNote(subjectDetails.getHomeworkNote());
        return subjectService.saveSubject(subject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok().build();
    }
}