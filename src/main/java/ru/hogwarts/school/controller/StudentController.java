package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }


    //Create
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return service.addStudent(student);
    }


    //Read
    @GetMapping
    public List<Student> getAllStudent() {
        return service.getAllStudent();
    }


    @GetMapping("id ")
    public Student findById(@RequestParam long id) {
        return service.findById(id);
    }

    @GetMapping("search")
    public List<Student> searchByAge(@RequestParam("age") int age) {
        return service.searchByAge(age);
    }


    //Update
    @PutMapping
    public Student changeStudent(@RequestBody Student student) {
        return service.changeStudent(student);
    }

    //Delete
    @DeleteMapping
    public void deleteStudent(@RequestParam long id) {
        service.deleteStudent(id);
    }

}
