package ru.hogwarts.school.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public class FacultyGetDTO {
    private Long id;
    private String name;
    private String color;
    @JsonIgnore
    private List<Student> students;

    public FacultyGetDTO(Faculty faculty) {
        if (faculty != null) {
            this.id = faculty.getId();
            this.name= faculty.getName();
            this.color = faculty.getColor();
            this.students = faculty.getStudents();
        }

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

}
