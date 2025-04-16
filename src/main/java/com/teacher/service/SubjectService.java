// SubjectService.java
package com.teacher.service;

import com.teacher.model.Subject;
import com.teacher.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> getSubjectsBySemesterAndTeacher(Integer semesterId, Long teacherId) {
        return subjectRepository.findBySemesterAndTeacher(semesterId, teacherId);
    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
    }

    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }

    @Transactional
    public void deleteSubjectsBySemesterAndTeacher(Integer semesterId, Long teacherId) {
        subjectRepository.deleteBySemesterAndTeacher(semesterId, teacherId);
    }
}