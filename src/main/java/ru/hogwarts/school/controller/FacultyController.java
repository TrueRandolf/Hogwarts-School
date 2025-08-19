package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return service.addFaculty(faculty);
    }

    //Read
    @GetMapping
    public List<Faculty> getAllFaculty() {
        return service.getAllFaculty();
    }


    @GetMapping("id")
    public Faculty findById(@RequestParam("id") long id) {
        return service.findById(id);
    }


    @GetMapping("search")
    public List<Faculty> searchByColor(@RequestParam("color") String color) {
        return service.searchByColor(color);
    }


    //Update
    @PutMapping
    public Faculty changeFaculty(@RequestBody Faculty faculty) {
        return service.changeFaculty(faculty);
    }

    //Delete
    @DeleteMapping
    public void deleteFaculty(@RequestParam long id) {
        service.deleteFaculty(id);
    }

}


