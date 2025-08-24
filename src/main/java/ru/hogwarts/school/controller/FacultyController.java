package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyGetDTO;
import ru.hogwarts.school.dto.FacultyPostDTO;
import ru.hogwarts.school.dto.StudentGetDTO;
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
    public long addFaculty(@RequestBody FacultyPostDTO facultyPostDTO) {
        return service.addFaculty(facultyPostDTO.getFacultyFromDTO()).getId();
    }

    //Read
    @GetMapping
    public List<FacultyGetDTO> getAllFaculty() {
        return service.getAllFaculty().stream()
                .map(FacultyGetDTO::new)
                .toList();
    }


    @GetMapping("/{id}")
    public FacultyGetDTO findById(@PathVariable("id") long id) {
        return new FacultyGetDTO(service.findById(id));
    }

    @GetMapping(params = "color")
    public List<FacultyGetDTO> searchByColor(@RequestParam("color") String color) {
        return service.searchByColor(color).stream()
                .map(FacultyGetDTO::new)
                .toList();
    }

    @GetMapping(params = "part")
    public List<FacultyGetDTO> searchByPart(@RequestParam("part") String part) {
        return service.searchByParty(part).stream()
                .map(FacultyGetDTO::new)
                .toList();
    }

    @GetMapping("/{id}/students")
    public List<StudentGetDTO> getFacultyStudents(@PathVariable("id") long id) {
        List<Student> students = service.findById(id).getStudents();
        return students.stream()
                .map(StudentGetDTO::new)
                .toList();
    }


    //Update
    @PutMapping("/{id}")
    public FacultyGetDTO changeFaculty(@RequestBody Faculty faculty) {
        return new FacultyGetDTO(service.changeFaculty(faculty));
    }

    //Delete
    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable long id) {
        service.deleteFaculty(id);
    }

}


