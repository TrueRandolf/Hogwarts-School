package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/*
@WebMvcTest
public class StudentControllerWebMVCTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService service;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private StudentController studentController;


    //
    final String facName = "Pw";
    final String facColor = "WHITE";
    final Faculty faculty = new Faculty(facName, facColor);
    final long idOne = 1L;
    final long idTwo = 2L;
    final String nameOne = "Mister One";
    final String nameTwo = "Mister Two";
    final int ageOne = 100;
    final int ageTwo = 200;

    Student studentOne = new Student();
    Student studentTwo = new Student();
    Student studentThree = new Student();
    List<Student> studentList = new LinkedList<>();

    @BeforeEach
    public void setStudentOne() {
        studentOne.setId(idOne);
        studentOne.setName(nameOne);
        studentOne.setAge(ageOne);
        studentOne.setFaculty(faculty);
    }

    private void setStudentTwo() {
        studentTwo.setId(idTwo);
        studentTwo.setName(nameTwo);
        studentTwo.setAge(ageTwo);
        studentTwo.setFaculty(faculty);
    }

    private void setStudentThree() {
        studentThree.setId(idOne);
        studentThree.setName(nameTwo);
        studentThree.setAge(ageTwo);
        studentThree.setFaculty(faculty);
    }

    private void setStudentList() {
        studentList.add(studentOne);
        studentList.add(studentTwo);
    }
    //

    @Test
    public void saveStudentTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", nameOne);
        studentObject.put("age", ageOne);
        when(studentRepository.save(any(Student.class))).thenReturn(studentOne);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(idOne));
    }

    @Test
    public void findByIdStudentTestPositive() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(studentOne));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(("/student/" + idOne))
                        .content(String.valueOf(idOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idOne))
                .andExpect(jsonPath("$.name").value(nameOne))
                .andExpect(jsonPath("$.age").value(ageOne));
    }

    @Test
    public void findByIdStudentTestNegative() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .get(("/student/" + idOne))                                     // а сюда добавился путь +ИД
                        .content(String.valueOf(idOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.code").value(404));
    }


    @Test
    public void getAllStudentTest() throws Exception {
        setStudentTwo();
        setStudentList();
        JSONObject studentObject = new JSONObject();
        when(studentRepository.findAll()).thenReturn(studentList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(idOne))
                .andExpect(jsonPath("$[0].name").value(nameOne))
                .andExpect(jsonPath("$[0].age").value(ageOne))
                .andExpect(jsonPath("$[1].id").value(idTwo))
                .andExpect(jsonPath("$[1].name").value(nameTwo))
                .andExpect(jsonPath("$[1].age").value(ageTwo));
    }


    @Test
    public void searchByAgeStudentTest() throws Exception {
        studentList.add(studentOne);
        when(studentRepository.findByAge(any(int.class))).thenReturn(studentList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(("/student/searchByAge"))
                        .param("age", String.valueOf(ageOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(idOne))
                .andExpect(jsonPath("$[0].name").value(nameOne))
                .andExpect(jsonPath("$[0].age").value(ageOne));
    }


    @Test
    public void searchBetweenAgeStudentTest() throws Exception {
        studentList.add(studentOne);
        when(studentRepository.findByAgeBetween(any(int.class), any(int.class))).thenReturn(studentList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(("/student/searchBetweenAge"))
                        .param("ageMin", String.valueOf(ageOne))
                        .param("ageMax", String.valueOf(ageTwo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[0].id").value(idOne))
                .andExpect(jsonPath("$[0].name").value(nameOne))
                .andExpect(jsonPath("$[0].age").value(ageOne));
    }


    @Test
    public void getStudentFacultyTestPositive() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(studentOne));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(("/student/" + idOne + "/faculty"))
                        .content(String.valueOf(idOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(facName))
                .andExpect(jsonPath("$.color").value(facColor));
    }


    @Test
    public void getStudentFacultyTestNegative() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .get(("/student/" + idOne + "/faculty"))
                        .content(String.valueOf(idOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.code").value("404"));
    }


    @Test
    public void changeStudentTestPositive() throws Exception {
        setStudentThree();
        System.out.println("studentThree = " + studentThree);
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", nameTwo);
        studentObject.put("age", ageTwo);

        when(studentRepository.findById(any(long.class))).thenReturn(Optional.of(studentOne));
        when(studentRepository.save(any(Student.class))).thenReturn(studentThree);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(("/student/" + idOne))
                        .content(String.valueOf(studentObject.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(nameTwo))
                .andExpect(jsonPath("$.age").value(ageTwo));
    }


    @Test
    public void changeStudentTestNegative() throws Exception {
        setStudentThree();
        System.out.println("studentThree = " + studentThree);
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", nameTwo);
        studentObject.put("age", ageTwo);
        when(studentRepository.findById(any(long.class))).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenReturn(studentThree);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(("/student/" + idOne))
                        .content(String.valueOf(studentObject.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.code").value("404"));
    }


    @Test
    public void deleteStudentTestPositive() throws Exception {
        when(studentRepository.findById(any(long.class))).thenReturn(Optional.of(studentOne));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(("/student/" + idOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteStudentTestNegative() throws Exception {
        when(studentRepository.findById(any(long.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(("/student/" + idOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }


}



 */