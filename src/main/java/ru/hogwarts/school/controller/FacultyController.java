package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.*;
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
    public long addFaculty(@RequestBody FacultyFromDTO facultyFromDTO) {
        return service.addFaculty(new FacultyMapper().dToS(facultyFromDTO)).getId();
    }

    //Read
    @GetMapping
    public List<FacultyToDTO> getAllFaculty() {
        return new FacultyMapper().sToD(service.getAllFaculty());
    }


    @GetMapping("/{id}")
    public FacultyToDTO findById(@PathVariable("id") long id) {
        return new FacultyMapper().sToD(service.findById(id));
    }

    //@GetMapping(params = "color")
    @GetMapping("searchByColor")
    public List<FacultyToDTO> searchByColor(@RequestParam("color") String color) {
        return new FacultyMapper().sToD(service.searchByColor(color));
    }

    //@GetMapping(params = "part")
    @GetMapping("searchByPart")
    public List<FacultyToDTO> searchByPart(@RequestParam("part") String part) {
        return new FacultyMapper().sToD(service.searchByPart(part));
    }

    @GetMapping("/{id}/students")
    public List<StudentToDTO> getFacultyStudents(@PathVariable("id") long id) {
        return new StudentMapper().sToD(service.findById(id).getStudents());
    }


    //Update
    @PutMapping("/{id}")
    public FacultyToDTO changeFaculty(@PathVariable long id, @RequestBody FacultyFromDTO facultyFromDTO) {
        Faculty faculty = new FacultyMapper().dToS(facultyFromDTO);
        faculty.setId(id); // Что это? Это - чтобы не засорять JSON лишним полем Id, которое и так вводится в PathVariable. Рутину - роботам
        return new FacultyMapper().sToD(service.changeFaculty(faculty));
    }

    //Delete
    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable long id) {
        service.deleteFaculty(id);
    }

}


