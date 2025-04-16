package com.teacher.model;


import jakarta.persistence.*;


@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String classroom;
    private String classTime;
    private String completionNote;
    private String homeworkNote;
    
    @ManyToOne
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getClassroom() { return classroom; }
    public void setClassroom(String classroom) { this.classroom = classroom; }
    public String getClassTime() { return classTime; }
    public void setClassTime(String classTime) { this.classTime = classTime; }
    public String getCompletionNote() { return completionNote; }
    public void setCompletionNote(String completionNote) { this.completionNote = completionNote; }
    public String getHomeworkNote() { return homeworkNote; }
    public void setHomeworkNote(String homeworkNote) { this.homeworkNote = homeworkNote; }
    public Semester getSemester() { return semester; }
    public void setSemester(Semester semester) { this.semester = semester; }
    public User getTeacher() { return teacher; }
    public void setTeacher(User teacher) { this.teacher = teacher; }
}