package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;


@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    //Create
    @PostMapping
    public Long addFaculty(@RequestBody Faculty faculty) {
        return service.addFaculty(faculty).getId();
    }

    //Read
    @GetMapping
    public List<Faculty> getAllFaculty() {
        return service.getAllFaculty();
    }

    @GetMapping("{id}")
    public Faculty findById(@PathVariable("id") long id) {
        return service.findById(id);
    }

    @GetMapping(params = "color")
    public List<Faculty> searchByColor(@RequestParam("color") String color) {
        return service.searchByColor(color);
    }

    @GetMapping(params = "part")
    public List<Faculty> searchByPart(@RequestParam("part") String part) {
        return service.searchByParty(part);
    }

    @GetMapping("/getStudents/{facultyId}")
    public List<Student> getFacultyStudents(@PathVariable("facultyId") long facultyId) {
        return service.findById(facultyId).getStudents();
    }


    //Update
    @PutMapping("/{id}")
    public Faculty changeFaculty(@RequestBody Faculty faculty) {
        return service.changeFaculty(faculty);
    }

    //Delete
    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable long id) {
        service.deleteFaculty(id);
    }

}


