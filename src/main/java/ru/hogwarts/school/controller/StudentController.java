package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
    public long addStudent(@RequestBody Student student) {
        return service.addStudent(student).getId();
    }


    //Read
    @GetMapping
    public List<Student> getAllStudent() {
        return service.getAllStudent();
    }


    @GetMapping("/{id}")
    public Student findById(@PathVariable long id) {
        return service.findById(id);
    }


    @GetMapping(params = "age")
    public List<Student> searchByAge(@RequestParam("age") int age) {
        return service.searchByAge(age);
    }

    @GetMapping(params = {"ageMin", "ageMax"})
    public List<Student> searchByAge(@RequestParam("ageMin") int ageMin,
                                     @RequestParam("ageMax") int ageMax) {
        return service.searchByAgeBetween(ageMin, ageMax);
    }

    @GetMapping("/getFaculty/{studentId}")
    public Faculty getStudentFaculty(@PathVariable ("studentId") long studentId) {
        return service.findById(studentId).getFaculty();
    }

    //Update
    @PutMapping("/{id}")
    public Student changeStudent(@PathVariable long id, @RequestBody Student student) {
        return service.changeStudent(student);
    }

    //Delete
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable long id) {
        service.deleteStudent(id);
    }

}
