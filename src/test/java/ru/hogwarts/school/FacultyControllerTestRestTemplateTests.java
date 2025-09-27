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

import java.util.LinkedList;
import java.util.List;

/*
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class FacultyControllerTestRestTemplateTests {
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
    final String nameOne = "Mister One";
    final String nameTwo = "Mister Two";
    final int ageOne = 10;
    final int ageTwo = 20;

    Faculty facultyOne = new Faculty();
    Faculty facultyTwo = new Faculty();
    Faculty facultyThree = new Faculty();
    final String fNameOne = "Faculty One";
    final String fNameTwo = "Faculty Two";
    final String fNameThree = "Faculty Three";
    final String fColorOne = "ColorOne";
    final String fColorTwo = "ColorTwo";
    final String fColorThree = "ColorThree";


    @BeforeEach
    void setStudents() {
        facultyOne.setName(fNameOne);
        facultyOne.setColor(fColorOne);
        facultyTwo.setName(fNameTwo);
        facultyTwo.setColor(fColorTwo);
        facultyThree.setName(fNameThree);
        facultyThree.setColor(fColorThree);

        List<Student> listOne = new LinkedList<>();
        List<Student> listTwo = new LinkedList<>();
        listOne.add(studentOne);
        listTwo.add(studentTwo);

        facultyOne.setStudents(listOne);
        facultyTwo.setStudents(listTwo);

        this.facultyService.addFaculty(facultyOne);
        this.facultyService.addFaculty(facultyTwo);

        studentOne.setName(nameOne);
        studentOne.setAge(ageOne);
        studentOne.setFaculty(facultyOne);

        studentTwo.setName(nameTwo);
        studentTwo.setAge(ageTwo);
        studentTwo.setFaculty(facultyTwo);

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
    public void addFacultyTest() throws Exception {
        String url = ("http://localhost:" + port + "/faculty");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        Faculty faculty = new Faculty("FacultyTemp", "ColorTemp");
        long returnedId;

        ResponseEntity<String> responseEntity = this.testRestTemplate
                .postForEntity(builder.toUriString(), faculty, String.class);
        returnedId = Long.parseLong(String.valueOf(responseEntity.getBody()));

        facultyService.findById(returnedId);
        Assertions
                .assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(facultyService.findById(returnedId).getName()).isEqualTo(faculty.getName());
        Assertions
                .assertThat(facultyService.findById(returnedId).getColor()).isEqualTo(faculty.getColor());
        facultyService.deleteFaculty(returnedId);

    }


    //GET Tests
    @Test
    public void getAllFacultyTest() throws Exception {
        String url = ("http://localhost:" + port + "/faculty");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<List<Faculty>> response = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Faculty>>() {
                });
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody()).isNotEmpty();

    }

    @Test
    public void findByIdTestPositive() throws Exception {
        final long idOne = facultyOne.getId();
        String url = ("http://localhost:" + port + "/faculty/" + idOne);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<Faculty> response = this.testRestTemplate
                .getForEntity(builder.toUriString(), Faculty.class);
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody()).isEqualTo(facultyOne);
    }

    @Test
    public void findByIdTestNegative() throws Exception {
        final long wrongId = -100L;
        String url = ("http://localhost:" + port + "/faculty/" + wrongId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<String> responseEntity = this.testRestTemplate
                .getForEntity(builder.toUriString(), String.class);
        Assertions
                .assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    public void searchByColorTestPositive() throws Exception {
        String url = ("http://localhost:" + port + "/faculty/searchByColor");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("color", fColorOne);
        ResponseEntity<List<Faculty>> response = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Faculty>>() {
                });
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody().get(0)).isEqualTo(facultyOne);
    }

    @Test
    public void searchByColorTestNegative() throws Exception {
        final String wrongColor = "----";
        String url = ("http://localhost:" + port + "/faculty/searchByColor");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("color", wrongColor);
        List<Faculty> list = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Faculty>>() {
                }).getBody();
        Assertions
                .assertThat(list).isEmpty();
    }


    @Test
    public void searchByPartTestPositive() throws Exception {
        String part = "col";
        String url = ("http://localhost:" + port + "/faculty/searchByPart");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("part", part);
        ResponseEntity<List<Faculty>> response = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Faculty>>() {
                });

        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody().get(0)).isEqualTo(facultyOne);
        Assertions
                .assertThat(response.getBody().get(1)).isEqualTo(facultyTwo);
    }


    @Test
    public void getFacultyStudentsPositive() throws Exception {
        final long idOne = facultyOne.getId();
        String url = ("http://localhost:" + port + "/faculty/" + idOne + "/students");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<List<Student>> response = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
                });
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(response.getBody().get(0)).isEqualTo(facultyOne.getStudents().get(0));
    }

    // UPDATE Tests
    @Test
    public void changeFacultyTestPositive() throws Exception {
        final long id = facultyOne.getId();
        String url = ("http://localhost:" + port + "/faculty/" + id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Faculty> facultyHttpEntity = new HttpEntity<>(facultyThree, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<Faculty> response
                = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.PUT, facultyHttpEntity, Faculty.class);
        Faculty faculty = response.getBody();
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions
                .assertThat(faculty.getId()).isEqualTo(facultyOne.getId());
        Assertions
                .assertThat(faculty.getName()).isEqualTo(facultyThree.getName());
        Assertions
                .assertThat(faculty.getColor()).isEqualTo(facultyThree.getColor());

    }

    @Test
    public void changeFacultyTestNegative() throws Exception {
        final long wrongId = -100L;
        String url = ("http://localhost:" + port + "/faculty/" + wrongId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Faculty> facultyHttpEntity = new HttpEntity<>(facultyThree, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        ResponseEntity<Faculty> response
                = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.PUT, facultyHttpEntity, Faculty.class);
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // DELETE Tests
    @Test
    public void deleteFacultyTestPositive() throws Exception {
        final long id = this.facultyService.addFaculty(facultyThree).getId();
        String url = ("http://localhost:" + port + "/faculty/" + id);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        // удаляю и жду ответ 200
        ResponseEntity<Faculty> response = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.DELETE, null, Faculty.class);
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
    public void deleteFacultyTestNegative() throws Exception {
        final long wrongId = -100L;
        String url = ("http://localhost:" + port + "/faculty/" + wrongId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        // удаляю и жду 404
        ResponseEntity<Faculty> response = this.testRestTemplate
                .exchange(builder.toUriString(), HttpMethod.DELETE, null, Faculty.class);
        Assertions
                .assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


}


 */