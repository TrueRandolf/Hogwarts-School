package ru.hogwarts.school;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hogwarts.school.exceptions.NotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;

@SpringBootTest
@Transactional
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    //CREATE TEST
    @Test
    public void whenAddStudentToDataBase_GivenFixedNameAge_ThenReturnStudentClassAndNameAndAge() {
        String name = "Dumbledoor";
        int age = 100;
        Student student = new Student(name, age);
        Assertions.assertEquals(Student.class, (studentService.addStudent(student)).getClass());
        Assertions.assertEquals(name, studentService.addStudent(student).getName());
        Assertions.assertEquals(age, studentService.addStudent(student).getAge());
    }

    //READ TEST
    @Test
    public void whenAddStudent_GivenFindById_ThenReturnRight() {
        String name = "Dumbledoor";
        int age = 100;
        long id = studentService.addStudent(new Student(name, age)).getId();
        Assertions.assertEquals(name, studentService.findById(id).getName());
        Assertions.assertEquals(age, studentService.findById(id).getAge());
    }

    @Test
    public void whenAddStudent_GivenFindByIdWrongId_ThenReturnSchoolException() {
        String name = "Dumbledoor";
        int age = 100;
        Exception exception = null;
        try {
            studentService.findById(-1L);
        } catch (Exception e) {
            exception = e;
        }
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
    }


    @Test
    public void whenAddStudent_GivenSearchByAge_ThenReturnListClass() {
        String name = "Dumbledoor";
        int age = 100;
        long id = studentService.addStudent(new Student(name, age)).getId();
        Assertions.assertEquals(ArrayList.class, studentService.searchByAge(age).getClass());
    }

    @Test
    public void whenAddStudent_GivenGetAllStudent_ThenReturnListClass() {
        String name = "Dumbledoor";
        int age = 100;
        long id = studentService.addStudent(new Student(name, age)).getId();
        Assertions.assertEquals(ArrayList.class, studentService.getAllStudent().getClass());
    }

    //UPDATE TEST
    @Test
    public void whenAddStudent_GivenChangeStudentRightId_ThenReturnEditedStudent() {
        String name = "Dumbledoor";
        int age = 100;
        Student student1 = studentService.addStudent(new Student(name, age));
        long id = student1.getId();

        String newName = "VolanDeMort";
        int newAge = 50;
        Student student2 = new Student(newName, newAge);
        student2.setId(id);

        Student student3;
        student3 = studentService.changeStudent(student2);
        Assertions.assertEquals(newName, student3.getName());
        Assertions.assertEquals(newAge, student3.getAge());

    }

    @Test
    public void whenAddStudent_GivenChangeStudentWrongId_ThenReturnSchoolException() {
        String name = "Dumbledoor";
        int age = 100;
        Student student = new Student(name, age);
        student.setId(-1L);
        Exception exception = null;
        try {
            studentService.changeStudent(student);
        } catch (Exception e) {
            exception = e;
        }
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
    }

    //DELETE TEST
    @Test
    public void whenAddStudent_GivenDeleteStudentRightId_ThenReturnDeletedStudent() {
        String name = "Dumbledoor";
        int age = 100;
        Student student = studentService.addStudent(new Student( name, age));
        long id = student.getId();
        Student returnedStudent = studentService.deleteStudent(id);
        Assertions.assertEquals(name, returnedStudent.getName());
        Assertions.assertEquals(age, returnedStudent.getAge());
    }

    @Test
    public void whenAddStudent_GivenDeleteStudentWrongId_ThenReturnSchoolException() {
        String name = "Dumbledoor";
        int age = 100;
        Student student = new Student(name, age);
        Exception exception = null;
        try {
            studentService.deleteStudent(-1L);
        } catch (Exception e) {
            exception = e;
        }
        Assertions.assertEquals(NotFoundException.class, exception.getClass());
    }

}


