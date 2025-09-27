package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

/*
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplateTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    Student studentOne = new Student();
    Student studentTwo = new Student();
    Student studentThree = new Student();
    final String nameOne = "Mister One";
    final String nameTwo = "Mister Two";
    final String nameThree = "Mister Three";
    final int ageOne = 10;
    final int ageTwo = 20;
    final int ageThree = 30;

    Faculty facultyOne = new Faculty();
    Faculty facultyTwo = new Faculty();
    final String fNameOne = "Faculty One";
    final String fNameTwo = "Faculty Two";
    final String fColorOne = "Color One";
    final String fColorTwo = "Color Two";


    @BeforeEach
    void setStudents() {
        facultyOne.setName(fNameOne);
        facultyOne.setColor(fColorOne);
        facultyTwo.setName(fNameTwo);
        facultyTwo.setColor(fColorTwo);

        this.facultyService.addFaculty(facultyOne);
        this.facultyService.addFaculty(facultyTwo);

        studentOne.setName(nameOne);
        studentOne.setAge(ageOne);
        studentOne.setFaculty(facultyOne);

        studentTwo.setName(nameTwo);
        studentTwo.setAge(ageTwo);
        studentTwo.setFaculty(facultyTwo);

        studentThree.setName(nameThree);
        studentThree.setAge(ageThree);

        this.studentService.addStudent(studentOne);
        this.studentService.addStudent(studentTwo);
    }

    @AfterEach
    void clearAll() {
        this.studentService.deleteStudent(studentOne.getId());
        this.studentService.deleteStudent(studentTwo.getId());

        this.facultyService.deleteFaculty(facultyOne.getId());
        this.facultyService.deleteFaculty(facultyTwo.getId());

    }

    //POST Test
    @Test
    public void addStudentTest() throws Exception {
        String url = ("http://localhost:" + port + "/student");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        Student student = new Student("Mister 100", 1000);
        long returnedId;

        ResponseEntity<String> responseEntity = this.testRestTemplate
                .postForEntity(builder.toUriString(), student, String.class);
        returnedId = Long.parseLong(String.valueOf(responseEntity.getBody()));

        studentService.findById(returnedId);
        Assertions
                .assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(studentService.findById(returnedId).getName()).isEqualTo(student.getName());
        Assertions
                .assertThat(studentService.findById(returnedId).getAge()).isEqualTo(student.getAge());
        studentService.deleteStudent(returnedId);

    }


    //GET Tests
    @Test
    public void getAllStudentTest() throws Exception {
        String url = ("http://localhost:" + port + "/student");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<List<Student>> response = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
                });
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void findByIdTestPositive() throws Exception {
        final long idOne = studentOne.getId();
        String url = ("http://localhost:" + port + "/student/" + idOne);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<Student> response = this.testRestTemplate
                .getForEntity(builder.toUriString(), Student.class);
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody()).isEqualTo(studentOne);
    }

    @Test
    public void findByIdTestNegative() throws Exception {
        final long wrongId = -100L;
        String url = ("http://localhost:" + port + "/student/" + wrongId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<String> responseEntity = this.testRestTemplate
                .getForEntity(builder.toUriString(), String.class);
        Assertions
                .assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void searchByAgeTestPositive() throws Exception {
        String url = ("http://localhost:" + port + "/student/searchByAge");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("age", ageOne);

        ResponseEntity<List<Student>> response = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
                });
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody().get(0)).isEqualTo(studentOne);
    }

    @Test
    public void searchByAgeTestNegative() throws Exception {
        final int wrongAge = -100;
        String url = ("http://localhost:" + port + "/student/searchByAge");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("age", wrongAge);
        List<Student> list = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
                }).getBody();
        Assertions
                .assertThat(list).isEmpty();
    }


    @Test
    public void searchBetweenAgeTestPositive() throws Exception {
        final int ageMin = ageOne - 1;
        final int ageMax = ageTwo + 1;

        String url = ("http://localhost:" + port + "/student/searchBetweenAge");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("ageMin", ageMin)
                .queryParam("ageMax", ageMax);
        ResponseEntity<List<Student>> response = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
                });

        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody().get(0)).isEqualTo(studentOne);
        Assertions
                .assertThat(response.getBody().get(1)).isEqualTo(studentTwo);
    }

    @Test
    public void getStudentFacultyPositive() throws Exception {
        final long idOne = studentOne.getId();
        String url = ("http://localhost:" + port + "/student/" + idOne + "/faculty");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<Faculty> response = this.testRestTemplate
                .getForEntity(builder.toUriString(), Faculty.class);
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody()).isEqualTo(studentOne.getFaculty());
    }

    // Update Tests
    @Test
    public void changeStudentTestPositive() throws Exception {
        final long id = studentOne.getId();
        String url = ("http://localhost:" + port + "/student/" + id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Student> studentHttpEntity = new HttpEntity<>(studentThree, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<Student> response
                = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.PUT, studentHttpEntity, Student.class);
        Student student = response.getBody();
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(student.getId()).isEqualTo(studentOne.getId());
        Assertions
                .assertThat(student.getName()).isEqualTo(studentThree.getName());
        Assertions
                .assertThat(student.getAge()).isEqualTo(studentThree.getAge());

    }

    @Test
    public void changeStudentTestNegative() throws Exception {
        final long wrongId = -100L;
        String url = ("http://localhost:" + port + "/student/" + wrongId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Student> studentHttpEntity = new HttpEntity<>(studentThree, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<Student> response
                = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.PUT, studentHttpEntity, Student.class);
        Student student = response.getBody();
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    // Delete Tests
    @Test
    public void deleteStudentTestPositive() throws Exception {
        final long id = this.studentService.addStudent(studentThree).getId();
        String url = ("http://localhost:" + port + "/student/" + id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        // удаляю и жду ответ 200
        ResponseEntity<Student> response = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.DELETE, null, Student.class);
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // ищу удалённый и закономерно жду 404
        ResponseEntity<String> responseEntity = this.testRestTemplate
                .getForEntity(builder.toUriString(), String.class);
        Assertions
                .assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        // опосредованно проверил
    }

    @Test
    public void deleteStudentTestNegative() throws Exception {
        final long wrongId = -100L;
        String url = ("http://localhost:" + port + "/student/" + wrongId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        // удаляю и жду 404
        ResponseEntity<Student> response = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.DELETE, null, Student.class);
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}


 */