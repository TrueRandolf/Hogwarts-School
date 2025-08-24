package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import ru.hogwarts.school.dto.FacultyGetDTO;
import ru.hogwarts.school.dto.StudentGetDTO;
import ru.hogwarts.school.dto.StudentPostDTO;
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
    public long addStudent(@RequestBody StudentPostDTO studentDTO) {
        return service.addStudent(studentDTO.getStudentFromDTO()).getId();
    }


    //Read
    @GetMapping
    public List<StudentGetDTO> getAllStudent() {
        List<Student> students = service.getAllStudent();
        return students.stream()
                .map(StudentGetDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public StudentGetDTO findById(@PathVariable long id) {
        return new StudentGetDTO(service.findById(id));
    }

    @GetMapping(params = "age")
    public List<StudentGetDTO> searchByAge(@RequestParam("age") int age) {
        List<Student> students = service.searchByAge(age);
        return students.stream()
                .map(StudentGetDTO::new)
                .toList();
    }

    @GetMapping(params = {"ageMin", "ageMax"})
    public List<StudentGetDTO> searchByAge(@RequestParam("ageMin") int ageMin,
                                           @RequestParam("ageMax") int ageMax) {
        List<Student> students = service.searchByAgeBetween(ageMin, ageMax);
        return students.stream()
                .map(StudentGetDTO::new)
                .toList();
    }

    @GetMapping("/{id}/faculty")
    public FacultyGetDTO getStudentFaculty(@PathVariable("id") long id) {
        return new FacultyGetDTO(service.findById(id).getFaculty());
    }

    //Update
    @PutMapping("/{id}")
    public StudentGetDTO changeStudent(@PathVariable long id, @RequestBody Student student) {
        return new StudentGetDTO((service.changeStudent(student)));
    }

    //Delete
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable long id) {
        service.deleteStudent(id);
    }

}
