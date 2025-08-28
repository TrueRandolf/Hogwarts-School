package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
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
        setDefaultValues();
    }

    private void setDefaultValues() {
        if (studentRepository.count() == 0) {
            addStudent(new Student("Harry Potter", 15));
            addStudent(new Student("Luna Lovegood", 15));
            addStudent(new Student("Draco Malfoy", 15));
            addStudent(new Student("Susan Bones", 15));
        }
    }


    //CREATE
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    //READ

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new NotFoundException("Нет студента c таким id"));
    }

    public List<Student> searchByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> searchByAgeBetween(int ageMin, int ageMax) {
        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }


    //UPDATE
    public Student changeStudent(Student student) {
        Optional<Student> obj = studentRepository.findById(student.getId());
        if (obj.isPresent()) {
            Faculty faculty = obj.get().getFaculty();
            student.setFaculty(faculty);
            studentRepository.save(student);
            return student;
        }
        throw new NotFoundException("Нет студента c таким id");
    }

    //DELETE
    // Или лишнего навертел, или: два раза проверь - один раз удали.
    public Student deleteStudent(long id) {
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
        throw new NotFoundException("Нет студента c таким id");
    }

    @Transactional
    // а вот и совместное удаление пары
    private void deleteCouple(long id) {
        long aid = avatarRepository.findByStudentId(id).get().getId(); // устал JPA объяснять, что удлять надо по Id студента. Ему было строго пофиг
        avatarRepository.deleteById(aid);
        studentRepository.deleteById(id);
    }

    // AVATAR BLOCK
    // R
    public Avatar findAvatar(long studentId) {
        return avatarRepository.findByStudentId(studentId).orElseThrow(() -> new NotFoundException("Не найден аватар для студента c таким id"));
    }

    // C
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
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
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private byte[] makeAvatarPreview(Path filePath) throws IOException {
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
