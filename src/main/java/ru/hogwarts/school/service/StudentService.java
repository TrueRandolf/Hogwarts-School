package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.NotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        setDefaultValues();
    }

    private void setDefaultValues() {
        if (studentRepository.count() == 0) {
            addStudent(new Student("Harry Potter", 15));
            addStudent(new Student("Luna Lovegood", 15));
            addStudent(new Student("Draco Malfoy", 15));
            addStudent(new Student("Susan Bones", 15));
        }
    }


    //CREATE
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    //READ
    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new NotFoundException("Нет студента c таким id"));
    }

    public List<Student> searchByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    //UPDATE
    public Student changeStudent(Student student) {
        Optional<Student> obj = studentRepository.findById(student.getId());
        if (obj.isPresent()) {
            studentRepository.save(student);
            return student;
        }
        throw new NotFoundException("Нет студента c таким id");
    }

    //DELETE
    public Student deleteStudent(long id) {
        Optional<Student> obj = studentRepository.findById(id);
        if (obj.isPresent()) {
            studentRepository.delete(obj.get());
            return obj.get();
        }
        throw new NotFoundException("Нет студента c таким id");
    }
}
