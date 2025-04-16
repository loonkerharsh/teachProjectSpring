package com.teacher.service;

import java.util.*;
import com.teacher.model.Semester;
import com.teacher.model.Subject;
import com.teacher.model.User;
import com.teacher.repository.SubjectRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SemesterService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getSubjectsBySemesterAndTeacher(Integer semesterId, Long teacherId) {
        return subjectRepository.findBySemesterAndTeacher(semesterId, teacherId);
    }

    @Transactional
    public void deleteSubjectsBySemesterAndTeacher(Integer semesterId, Long teacherId) {
        subjectRepository.deleteBySemesterAndTeacher(semesterId, teacherId);
    }
}

