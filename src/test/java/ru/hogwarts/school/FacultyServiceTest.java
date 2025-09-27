package ru.hogwarts.school;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hogwarts.school.exceptions.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
/*
@SpringBootTest
@Transactional
public class FacultyServiceTest {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private FacultyRepository facultyRepository;

    //CREATE TEST
    @Test
    public void whenAddFacultyToDataBase_GivenFixedNameAge_ThenReturnfacultyClassAndNameAndAge() {
        String name = "Griffenduy";
        String color = "MAGENTA";
        Faculty faculty = new Faculty(name, color);
        Assertions.assertEquals(Faculty.class, (facultyService.addFaculty(faculty).getClass()));
        Assertions.assertEquals(name, facultyService.addFaculty(faculty).getName());
        Assertions.assertEquals(color, facultyService.addFaculty(faculty).getColor());
    }

    //READ TEST
    @Test
    public void whenAddFaculty_GivenFindById_ThenReturnRight() {
        String name = "Griffenduy";
        String color = "MAGENTA";
        long id = facultyService.addFaculty(new Faculty(name, color)).getId();
        Assertions.assertEquals(name, facultyService.findById(id).getName());
        Assertions.assertEquals(color, facultyService.findById(id).getColor());
    }

    @Test
    public void whenAddFaculty_GivenFindByIdWrongId_ThenReturnSchoolException() {
        String name = "Griffenduy";
        String color = "MAGENTA";
        Exception exception = null;
        try {
            facultyService.findById(-1L);
        } catch (Exception e) {
            exception = e;
        }
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
    }


    @Test
    public void whenAddFaculty_GivenSearchByAge_ThenReturnListClass() {
        String name = "Griffenduy";
        String color = "MAGENTA";
        long id = facultyService.addFaculty(new Faculty(name, color)).getId();
        Assertions.assertEquals(ArrayList.class, facultyService.searchByColor(color).getClass());
    }

    @Test
    public void whenAddFaculty_GivenGetAllFaculty_ThenReturnListClass() {
        String name = "Griffenduy";
        String color = "MAGENTA";
        long id = facultyService.addFaculty(new Faculty(name, color)).getId();
        Assertions.assertEquals(ArrayList.class, facultyService.getAllFaculty().getClass());
    }

    //UPDATE TEST
    @Test
    public void whenAddFaculty_GivenChangeFacultyRightId_ThenReturnEditedFaculty() {
        String name = "Griffenduy";
        String color = "MAGENTA";
        Faculty faculty1 = facultyService.addFaculty(new Faculty(name, color));
        long id = faculty1.getId();

        String newName = "RavenCluv";
        String newColor = "INFRARED";
        Faculty faculty2 = new Faculty(newName, newColor);
        faculty2.setId(id);

        Faculty faculty3;
        faculty3 = facultyService.changeFaculty(faculty2);
        Assertions.assertEquals(newName, faculty3.getName());
        Assertions.assertEquals(newColor, faculty3.getColor());

    }

    @Test
    public void whenAddfaculty_GivenChangeFacultyWrongId_ThenReturnSchoolException() {
        String name = "Griffenduy";
        String color = "MAGENTA";
        Faculty faculty = new Faculty(name, color);
        faculty.setId(-1L);
        Exception exception = null;
        try {
            facultyService.changeFaculty(faculty);
        } catch (Exception e) {
            exception = e;
        }
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
    }

    //DELETE TEST
    @Test
    public void whenAddFaculty_GivenDeleteFacultyRightId_ThenReturnDeletedFaculty() {
        String name = "Griffenduy";
        String color = "MAGENTA";
        Faculty faculty = facultyService.addFaculty(new Faculty(name, color));
        long id = faculty.getId();
        Faculty returnedfaculty = facultyService.deleteFaculty(id);
        Assertions.assertEquals(name, returnedfaculty.getName());
        Assertions.assertEquals(color, returnedfaculty.getColor());
    }

    @Test
    public void whenAddFaculty_GivenDeleteFacultyWrongId_ThenReturnSchoolException() {
        String name = "Griffenduy";
        String color = "MAGENTA";
        Faculty faculty = new Faculty(name, color);
        Exception exception = null;
        try {
            facultyService.deleteFaculty(-1L);
        } catch (Exception e) {
            exception = e;
        }
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
    }

}


 */