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
    @PostMapping("/add/{name},{age}")
    public Student addStudent(@PathVariable("name") String name, @PathVariable("age") int age) {
        return service.addStudent(name, age);
    }

    //Read
    @GetMapping("")
    public List<Student> getAllStudent() {
        return service.getAllStudent();
    }

    @GetMapping("/search/id")
    public Student findById(@RequestParam("id") long id) {
        return service.findById(id);
    }

    @GetMapping("/search/age")
    public List<Student> searchByAge(@RequestParam("age") int age) {
        return service.searchByAge(age);
    }

    //Update
    @PutMapping("/change")
    public Student changeStudent(@RequestBody Student student) {
        return service.changeStudent(student);
    }

    //Delete
    @DeleteMapping("/delete")
    public void deleteStudent(@RequestParam("id") long id) {
        service.deleteStudent(id);
    }

}
