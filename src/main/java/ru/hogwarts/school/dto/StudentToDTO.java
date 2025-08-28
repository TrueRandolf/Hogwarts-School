package ru.hogwarts.school.dto;

public class StudentToDTO {

    private long id;
    private String name;
    private int age;
    private Long facultyId;
    private String facultyName;

    public StudentToDTO() {

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

    public Long getFacId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
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

    public void setFacId(Long facId) {
        this.facultyId = facId;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
