package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;

import ru.hogwarts.school.exceptions.NotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> studentMap;
    private static long count;

    public StudentService() {

        this.studentMap = new HashMap<>();
        setDefaultValues();

    }

    private void setDefaultValues() {
        addStudent("qwert", 10);
        addStudent("asdfg", 20);
        addStudent("zxcvb", 30);
    }


    //CREATE
    public Student addStudent(String name, int age) {
        Student student = new Student(count, name, age);
        studentMap.put(count++, student);
        return student;
    }

    //READ
    public Student findById(long id) {
        if (studentMap.containsKey(id)) {
            return studentMap.get(id);
        }
        throw new NotFoundException("Нет такого студента");
    }

    public List<Student> searchByAge(int age) {
        return studentMap
                .values()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public List<Student> getAllStudent() {
        return new LinkedList<>(studentMap.values());
    }

    //UPDATE
    public Student changeStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
            return student;
        }
        throw new NotFoundException("Нет такого студента");
    }

    //DELETE
    public Student deleteStudent(long id) {
        if (studentMap.containsKey(id)) {
            Student student = studentMap.get(id);
            studentMap.remove(id);
            return student;
        }
        throw new NotFoundException("Нет такого студента");
    }

}
