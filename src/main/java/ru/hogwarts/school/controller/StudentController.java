package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import ru.hogwarts.school.dto.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.InfoService;
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
    public long addStudent(@RequestBody StudentFromDTO studFromDTO) {
        return service.addStudent(new StudentMapper().dToS(studFromDTO)).getId();
    }


    //Read
    @GetMapping
    public List<StudentToDTO> getAllStudent() {
        return new StudentMapper().sToD(service.getAllStudent());
    }

    @GetMapping("/{id}")
    public StudentToDTO findById(@PathVariable long id) {
        return new StudentMapper().sToD(service.findById(id));
    }

    @GetMapping("searchByAge")
    public List<StudentToDTO> searchByAge(@RequestParam("age") int age) {
        return new StudentMapper().sToD((service.searchByAge(age)));
    }

    @GetMapping("searchBetweenAge")
    public List<StudentToDTO> searchBetweenAge(@RequestParam("ageMin") int ageMin,
                                               @RequestParam("ageMax") int ageMax) {
        return new StudentMapper().sToD(service.searchByAgeBetween(ageMin, ageMax));
    }

    @GetMapping("/{id}/faculty")
    public FacultyToDTO getStudentFaculty(@PathVariable("id") long id) {
        return new FacultyMapper().sToD(service.findById(id).getFaculty());
    }

    @GetMapping("getNumberOfStudents")
    public Integer getNumberOfStudents() {
        return service.getNumberStudents();
    }

    @GetMapping("getAverageAge")
    public float getAverageAge() {
        return service.getAverageAge();
    }

    @GetMapping("getLastFive")
    public List<StudentToDTO> getLastFive() {
        return new StudentMapper().sToD((service.getLastFive()));
    }


    //Update
    @PutMapping("/{id}")
    public StudentToDTO changeStudent(@PathVariable long id, @RequestBody StudentFromDTO studFromDTO) {
        Student student = new StudentMapper().dToS(studFromDTO);
        student.setId(id);   // Что это? Это - чтобы не засорять JSON лишним полем Id, которое и так вводится в PathVariable. Рутину - роботам
        return new StudentMapper().sToD(service.changeStudent(student));
    }

    //Delete
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable long id) {
        service.deleteStudent(id);
    }

}
