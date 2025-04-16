// SemesterService.java
package com.teacher.service;

import com.teacher.model.Semester;
import com.teacher.model.User;
import com.teacher.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class SemesterService {
    private final SemesterRepository semesterRepository;

    @Autowired
    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    public Set<Semester> getSemestersByUser(User user) {
        return user.getSemesters();
    }
}