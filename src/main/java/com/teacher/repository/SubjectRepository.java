package com.teacher.repository;

import com.teacher.model.Subject;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    
    @Query("SELECT s FROM Subject s WHERE s.semester.id = :semesterId AND s.teacher.id = :teacherId")
    List<Subject> findBySemesterAndTeacher(@Param("semesterId") Integer semesterId, 
                                         @Param("teacherId") Long teacherId);
    
    @Modifying
    @Query("DELETE FROM Subject s WHERE s.semester.id = :semesterId AND s.teacher.id = :teacherId")
    void deleteBySemesterAndTeacher(@Param("semesterId") Integer semesterId, 
                                   @Param("teacherId") Long teacherId);
}