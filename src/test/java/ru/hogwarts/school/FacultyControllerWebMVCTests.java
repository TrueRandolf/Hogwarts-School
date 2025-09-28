package ru.hogwarts.school;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.dto.FacultyMapper;
import ru.hogwarts.school.exceptions.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMVCTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;


    //
    final long idOne = 10L;
    final long idTwo = 20L;
    final String nameOne = "Faculty One";
    final String nameTwo = "Faculty Two";
    final String colorOne = "Color One";
    final String colorTwo = "Color Two";
    Faculty facultyOne = new Faculty();
    Faculty facultyTwo = new Faculty();
    Faculty facultyThree = new Faculty();
    List<Faculty> facultyList = new LinkedList<>();
    List<Student> studentList = new LinkedList<>();

    @BeforeEach
    public void setFacultyOne() {
        facultyOne.setId(idOne);
        facultyOne.setName(nameOne);
        facultyOne.setColor(colorOne);
        facultyOne.setStudents(studentList);
    }

    private void setFacultyTwo() {
        facultyTwo.setId(idTwo);
        facultyTwo.setName(nameTwo);
        facultyTwo.setColor(colorTwo);
        facultyTwo.setStudents(studentList);
    }

    private void setFacultyThree() {
        facultyThree.setId(idOne);
        facultyThree.setName(nameTwo);
        facultyThree.setColor(colorTwo);
        facultyThree.setStudents(studentList);
    }

    private void setFacultyList() {
        facultyList.add(facultyOne);
        facultyList.add(facultyTwo);
    }


    private void setStudents() {
        Student studentOne = new Student("Mister One", 100);
        studentOne.setId(1);
        Student studentTwo = new Student("Mister Two", 200);
        studentTwo.setId(2);
        studentList.add(studentOne);
        studentList.add(studentTwo);
    }
    //


    @Test
    public void saveFacultyTest() throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", nameOne);
        facultyObject.put("color", colorOne);
        when(facultyService.addFaculty(any(Faculty.class))).thenReturn(facultyOne);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(idOne));
    }


    @Test
    public void findByIdFacultyTestPositive() throws Exception {
        when(facultyService.findById(any(Long.class))).thenReturn(facultyOne);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(("/faculty/" + idOne))
                        .content(String.valueOf(idOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idOne))
                .andExpect(jsonPath("$.name").value(nameOne))
                .andExpect(jsonPath("$.color").value(colorOne));
    }

    @Test
    public void findByIdFacultyTestNegative() throws Exception {
        when(facultyService.findById(any(Long.class))).thenThrow(NotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(("/faculty/" + idOne))
                        .content(String.valueOf(idOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.code").value(404));
    }


    @Test
    public void getAllFacultyTest() throws Exception {
        setFacultyTwo();
        setFacultyList();
        JSONObject facultyObject = new JSONObject();
        when(facultyService.getAllFaculty()).thenReturn(facultyList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(idOne))
                .andExpect(jsonPath("$[0].name").value(nameOne))
                .andExpect(jsonPath("$[0].color").value(colorOne))
                .andExpect(jsonPath("$[1].id").value(idTwo))
                .andExpect(jsonPath("$[1].name").value(nameTwo))
                .andExpect(jsonPath("$[1].color").value(colorTwo));
    }

    @Test
    public void searchByColorFacultyTest() throws Exception {
        facultyList.add(facultyOne);
        when(facultyService.searchByColor(any(String.class))).thenReturn(facultyList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(("/faculty/searchByColor"))
                        .param("color", String.valueOf(colorOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(idOne))
                .andExpect(jsonPath("$[0].name").value(nameOne))
                .andExpect(jsonPath("$[0].color").value(colorOne));
    }

    @Test
    public void searchByPartTest() throws Exception {
        facultyList.add(facultyOne);
        when(facultyService.searchByPart(any(String.class))).thenReturn(facultyList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(("/faculty/searchByPart"))
                        .param("part", String.valueOf("lll"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(idOne))
                .andExpect(jsonPath("$[0].name").value(nameOne))
                .andExpect(jsonPath("$[0].color").value(colorOne));
    }


    @Test
    public void getFacultyStudentsTestPositive() throws Exception {
        setStudents();
        setFacultyOne();
        System.out.println("facultyOne = " + new FacultyMapper().sToD(facultyOne));
        System.out.println("facultyOne = " + facultyOne.getStudents());
        when(facultyService.findById(any(Long.class))).thenReturn(facultyOne);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(("/faculty/" + idOne + "/students"))
                        .content(String.valueOf(idOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[0].name").value("Mister One"))
                .andExpect(jsonPath("$[1].name").value("Mister Two"));
    }


    @Test
    public void changeFacultyTestPositive() throws Exception {
        setFacultyThree();
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", nameTwo);
        facultyObject.put("color", colorTwo);

        when(facultyService.findById(any(long.class))).thenReturn(facultyOne);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(("/faculty/" + idOne))
                        .content(String.valueOf(facultyObject.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        when(facultyService.changeFaculty(any(Faculty.class))).thenReturn(facultyThree);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(("/faculty/" + idOne))
                        .content(String.valueOf(facultyObject.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(nameTwo))
                .andExpect(jsonPath("$.color").value(colorTwo));
    }

    @Test
    public void changeFacultyTestNegative() throws Exception {
        setFacultyThree();
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", nameTwo);
        facultyObject.put("color", colorTwo);
        when(facultyService.changeFaculty(any(Faculty.class))).thenThrow(NotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(("/faculty/" + idOne))
                        .content(String.valueOf(facultyObject.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.code").value("404"));
    }


    @Test
    public void deleteFacultyTestPositive() throws Exception {
        when(facultyService.deleteFaculty(any(long.class))).thenReturn(facultyOne);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(("/faculty/" + idOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFacultyTestNegative() throws Exception {
        when(facultyService.deleteFaculty(any(long.class))).thenThrow(NotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(("/faculty/" + idOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

}

