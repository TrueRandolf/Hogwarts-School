package ru.hogwarts.school.dto;

import ru.hogwarts.school.model.Faculty;

public record FacultyPostDTO(String name, String color) {
    public Faculty getFacultyFromDTO() {
        return new Faculty(name, color);
    }
}

