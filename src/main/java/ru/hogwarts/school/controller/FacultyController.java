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

    //C
    @PostMapping("/add/{name},{color}")
    public Faculty addFaculty(@PathVariable("name") String name, @PathVariable("color") String color) {
        return service.addFaculty(name, color);
    }

    //R
    @GetMapping("")
    public List<Faculty> getAllFaculty() {
        return service.getAllFaculty();
    }


    @GetMapping("/search/id")
    public Faculty findById(@RequestParam("id") long id) {
        return service.findById(id);
    }


    @GetMapping("/search/color")
    public List<Faculty> searchByColor(@RequestParam("color") String color) {
        return service.searchByColor(color);
    }


    //U
    @PutMapping("/change/{id}")
    public Faculty changeFaculty(@RequestBody Faculty faculty) {
        return service.changeFaculty(faculty);
    }

    //D
    @DeleteMapping("/delete")
    public void deleteFaculty(long id) {
        service.deleteFaculty(id);
    }


}


