package ru.hogwarts.school.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FacultyFromDTO {
    @JsonIgnore
    private long id;

    private String name;
    private String color;

    public FacultyFromDTO() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
