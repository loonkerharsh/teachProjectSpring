// SemesterRepository.java
package com.teacher.repository;

import com.teacher.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Integer> {
}