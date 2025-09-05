package ru.hogwarts.school.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StudentFromDTO {

    @JsonIgnore
    private long id;

    private String name;
    private int age;

    public StudentFromDTO() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
