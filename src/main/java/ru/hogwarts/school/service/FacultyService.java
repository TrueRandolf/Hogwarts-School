package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
        setDefaultValues();
    }

    private void setDefaultValues() {
        if (facultyRepository.count() == 0) {
            addFaculty("Griffindoor", "RED");
            addFaculty("Slizerin", "GREEN");
            addFaculty("Ravenclaw", "BLUE");
            addFaculty("Hufflepuff", "BROWN");
        }
    }

    //C
    public Faculty addFaculty(String name, String color) {
        return facultyRepository.save(new Faculty(name, color));
    }

    //R
    public Faculty findById(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new NotFoundException("Нет факультета с таким id"));
    }

    public List<Faculty> searchByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    //U
    public Faculty changeFaculty(Faculty faculty) {
        Optional<Faculty> obj = facultyRepository.findById(faculty.getId());
        if (obj.isPresent()) {
            facultyRepository.save(faculty);
            return faculty;
        }
        throw new NotFoundException("Нет факультета с таким id");
    }

    //D
    public Faculty deleteFaculty(long id) {
        Optional<Faculty> obj = facultyRepository.findById(id);
        if (obj.isPresent()) {
            facultyRepository.delete(obj.get());
            return obj.get();
        }
        throw new NotFoundException("Нет факультета с таким id");
    }

}
