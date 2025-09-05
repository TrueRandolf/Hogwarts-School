package ru.hogwarts.school.dto;

import ru.hogwarts.school.model.Student;

public record StudentPostDTO(String name, int age) {

    public Student getStudentFromDTO() {
        return new Student(this.name, this.age);
    }

}
