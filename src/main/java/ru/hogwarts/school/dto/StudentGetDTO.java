package ru.hogwarts.school.dto;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;


public class StudentGetDTO {
    private long id;
    private String name;
    private int age;
    private Long facultyId;
    private String facultyName;
    private Faculty faculty;

    public StudentGetDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.age = student.getAge();
        this.faculty = student.getFaculty();
        if (this.faculty != null) {
            this.facultyId = this.faculty.getId();
            this.facultyName = this.faculty.getName();
        }
    }

//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public void setFaculty(Faculty faculty) {
//        this.faculty = faculty;
//    }
//
//    public void setFacultyId(Long facultyId) {
//        this.facultyId = facultyId;
//    }
//
//    public void setFacultyName(String facultyName) {
//        this.facultyName = facultyName;
//    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

}
