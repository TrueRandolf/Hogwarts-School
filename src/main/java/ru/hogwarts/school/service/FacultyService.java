package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;

import ru.hogwarts.school.exceptions.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> facultyMap;
    private static long count;

    public FacultyService() {
        this.facultyMap = new HashMap<>();
        setDefaultValues();
    }

    private void setDefaultValues() {
        addFaculty("Griffindoor", "RED");
        addFaculty("Slizerin", "GREEN");
        addFaculty("Ravenclaw", "BLUE");
    }

    //C
    public Faculty addFaculty(String name, String color) {
        Faculty faculty = new Faculty(count, name, color);
        facultyMap.put(count++, faculty);
        return faculty;
    }

    //R
    public Faculty findById(long id) {
        if (facultyMap.containsKey(id)) {
            return facultyMap.get(id);
        }
        throw new NotFoundException("Нет такого факультета");
    }


    public List<Faculty> searchByColor(String color) {
        return facultyMap
                .values()
                .stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public List<Faculty> getAllFaculty() {
        return new LinkedList<>(facultyMap.values());
    }

    //U
    public Faculty changeFaculty(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            facultyMap.put(faculty.getId(), faculty);
            return faculty;
        }
        throw new NotFoundException("Нет такого факультета");

    }


    //D
    public Faculty deleteFaculty(long id) {
        if (facultyMap.containsKey(id)) {
            Faculty faculty = facultyMap.get(id);
            facultyMap.remove(id);
            return faculty;
        }
        throw new NotFoundException("Нет такого факультета");
    }


}
