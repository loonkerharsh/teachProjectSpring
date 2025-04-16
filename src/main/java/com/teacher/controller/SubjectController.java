package com.teacher.controller;

import com.teacher.model.Subject;
import com.teacher.model.User;
import com.teacher.repository.UserRepository;
import com.teacher.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRepository userRepository;

    @GetMapping
    public List<Subject> getSubjectsForSemester(
            @RequestParam Integer semesterId,
            @RequestParam Long teacherId) {
        // We no longer need to fetch the user first
        return subjectService.getSubjectsBySemesterAndTeacher(semesterId, teacherId);
    }

    @PostMapping
    public Subject addSubject(@RequestBody Subject subject) {
        return subjectService.saveSubject(subject);
    }

    @PutMapping("/{id}")
    public Subject updateSubject(@PathVariable Long id, @RequestBody Subject subjectDetails) {
        Subject subject = subjectService.getSubjectById(id);
        
        // Update fields
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