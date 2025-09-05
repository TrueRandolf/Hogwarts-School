package ru.hogwarts.school.dto;

import ru.hogwarts.school.model.Student;

import java.util.List;

public class StudentMapper {

    public StudentMapper() {
    }

    // S->DTO
    public StudentToDTO sToD(Student student) {
        StudentToDTO studDTOMap = new StudentToDTO();
        if (student != null) {
            studDTOMap.setId(student.getId());
            studDTOMap.setName(student.getName());
            studDTOMap.setAge(student.getAge());
        }
        if (student != null && student.getFaculty() != null) {
            studDTOMap.setFacId(student.getFaculty().getId());
            studDTOMap.setFacultyName(student.getFaculty().getName());
        }
        return studDTOMap;
    }

    // DTO->S
    public Student dToS(StudentToDTO studDTO) {
        Student student = new Student();
        if (studDTO != null) {
            student.setName(studDTO.getName());
            student.setAge(student.getAge());
        }
        return student;
    }

    // List<S>->List<DTO>
    public List<StudentToDTO> sToD(List<Student> students) {
        return students.stream()
                .map(this::sToD)
                .toList();

    }


    // POST/PUT DTO->S
    public Student dToS(StudentFromDTO studFromDTO) {
        Student student = new Student();
        if (studFromDTO != null) {
            student.setName(studFromDTO.getName());
            student.setAge(studFromDTO.getAge());
        }
        return student;
    }

}