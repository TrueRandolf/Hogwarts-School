package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        //setDefaultValues();
    }

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);


    private void setDefaultValues() {
        logger.debug("Was invoked private method for create default filling table FACULTY");
        if (facultyRepository.count() == 0) {
            addFaculty(new Faculty("Griffindoor", "RED"));
            addFaculty(new Faculty("Slizerin", "GREEN"));
            addFaculty(new Faculty("Ravenclaw", "BLUE"));
            addFaculty(new Faculty("Hufflepuff", "BROWN"));
        }
    }

    //C
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    //R
    public Faculty findById(long id) {
        logger.info("Was invoked method for get faculty by id: {}", id);
        return facultyRepository.findById(id).orElseThrow(() ->
                {
                    logger.error("Appeal to a non-existent id: {}", id);
                    return new NotFoundException("Нет факультета с таким id");
                }
        );
    }

    public List<Faculty> searchByColor(String color) {
        logger.info("Was invoked method for get faculty by color: {}", color);
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> searchByPart(String part) {
        logger.info("Was invoked method for get faculty by part NAME or COLOR: {}", part);
        return facultyRepository.searchByPart(part);
    }

    public List<Faculty> getAllFaculty() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    }


    //U
    public Faculty changeFaculty(Faculty faculty) {
        logger.info("Was invoked method for change faculty");
        Optional<Faculty> obj = facultyRepository.findById(faculty.getId());
        if (obj.isPresent()) {
            facultyRepository.save(faculty);
            return faculty;
        }
        logger.error("Appeal to a non-existent id: {}", faculty.getId());
        throw new NotFoundException("Нет факультета с таким id");
    }

    //D
    public Faculty deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty by id: {}", id);
        Optional<Faculty> obj = facultyRepository.findById(id);
        if (obj.isPresent()) {
            facultyRepository.delete(obj.get());
            return obj.get();
        }
        logger.error("Appeal to a non-existent id: {}", id);
        throw new NotFoundException("Нет факультета с таким id");
    }

}
