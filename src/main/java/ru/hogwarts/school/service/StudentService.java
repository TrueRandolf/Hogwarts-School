package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptions.NotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
        //setDefaultValues();
    }

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    // логгирование уровня INFO выполнено только для public методов
    // логгирование уровня ERROR выполнено только для public методов, в которых возможно исключение
    // логгирование уровня DEBUG выполнено только для private методов


    private void setDefaultValues() {
        logger.debug("Was invoked private method for create default filling table STUDENT");
        if (studentRepository.count() == 0) {
            addStudent(new Student("Harry Potter", 15));
            addStudent(new Student("Luna Lovegood", 15));
            addStudent(new Student("Draco Malfoy", 15));
            addStudent(new Student("Susan Bones", 15));
        }
    }

    //CREATE
    public Student addStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    //READ

    public List<Student> getAllStudent() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        logger.info("Was invoked method for find by student id: {}", id);
        //return studentRepository.findById(id).orElseThrow(() -> new NotFoundException("Нет студента c таким id"));
        return studentRepository.findById(id).orElseThrow(() ->
                {
                    logger.error("Appeal to a non-existent id: {}", id);
                    return new NotFoundException("Нет студента c таким id");
                }
        );

    }

    public List<Student> searchByAge(int age) {
        logger.info("Was invoked method for find by student age: {}", age);
        return studentRepository.findByAge(age);
    }

    public List<Student> searchByAgeBetween(int ageMin, int ageMax) {
        logger.info("Was invoked method for find students between ages: {}-{}", ageMin, ageMax);
        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }

    public Integer getNumberStudents() {
        logger.info("Was invoked method for get number of students");
        return studentRepository.getNumberOfStudents();
    }

    public float getAverageAge() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFive() {
        logger.info("Was invoked method for get last 5 students");
        return studentRepository.getLastFiveById();
    }


    //UPDATE
    public Student changeStudent(Student student) {
        logger.info("Was invoked method for change student");
        Optional<Student> obj = studentRepository.findById(student.getId());
        if (obj.isPresent()) {
            Faculty faculty = obj.get().getFaculty();
            student.setFaculty(faculty);
            studentRepository.save(student);
            return student;
        }
        logger.error("Appeal to a non-existent id: {}", student.getId());
        throw new NotFoundException("Нет студента c таким id");
    }

    //DELETE
    // Или лишнего навертел, или: два раза проверь - один раз удали.
    public Student deleteStudent(long id) {
        logger.info("Was invoked method for delete student by id: {}", id);
        Optional<Student> objStudent = studentRepository.findById(id);
        Optional<Avatar> objAvatar = avatarRepository.findByStudentId(id);
        // Если существует пара студент-аватар - удаляю в одной транзакции
        if (objStudent.isPresent() && objAvatar.isPresent()) {
            deleteCouple(id);
            return objStudent.get();
        }
        // Если только студент - то его и удаляю. Зачем удалять несуществующий аватар?
        if (objStudent.isPresent()) {
            studentRepository.deleteById(id);
            return objStudent.get();
        }
        logger.error("Appeal to a non-existent id: {}", id);
        throw new NotFoundException("Нет студента c таким id");
    }

    @Transactional
    // а вот и совместное удаление пары
    private void deleteCouple(long id) {
        logger.debug("Was invoked private method for delete couple student-avatar");
        long aid = avatarRepository.findByStudentId(id).get().getId(); // устал JPA объяснять, что удлять надо по Id студента. Ему было строго пофиг
        avatarRepository.deleteById(aid);
        studentRepository.deleteById(id);
    }

    // AVATAR BLOCK
    // R
    public Avatar findAvatar(long studentId) {
        logger.info("Was invoked method for get student avatar by student id: {}", studentId);
        return avatarRepository.findByStudentId(studentId).orElseThrow(() ->
                new NotFoundException("Не найден аватар для студента c таким id"));
    }


    public List<Avatar> getAllAvatar(int page, int size) {
        logger.info("Was invoked method for get all students avatar as JSON-list without media");
        Pageable pageable = PageRequest.of(page, size);
        return avatarRepository.findAll(pageable).getContent();
    }


    // C
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for upload student avatar by student id");
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Студент c таким id не существует"));
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(makeAvatarPreview(filePath));
        avatarRepository.save(avatar);
    }

    private String getExtensions(String fileName) {
        logger.debug("Was invoked private method for create avatar filename");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private byte[] makeAvatarPreview(Path filePath) throws IOException {
        logger.debug("Was invoked private method for make avatar preview");
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    // U

    // D

}
