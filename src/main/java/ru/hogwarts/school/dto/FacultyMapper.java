package ru.hogwarts.school.dto;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public class FacultyMapper {

    // F->DTO
    public FacultyToDTO sToD(Faculty faculty) {
        FacultyToDTO facultyDTOMap = new FacultyToDTO();
        if (faculty != null) {
            facultyDTOMap.setId(faculty.getId());
            facultyDTOMap.setName(faculty.getName());
            facultyDTOMap.setColor(faculty.getColor());
        }
        return facultyDTOMap;
    }

    // DTO->F
    public Faculty dToS(FacultyToDTO facultyToDTO) {
        Faculty faculty = new Faculty();
        if (facultyToDTO != null) {
            faculty.setName(facultyToDTO.getName());
            faculty.setColor(faculty.getColor());
        }
        return faculty;
    }

    // List<F>->List<DTO>
    public List<FacultyToDTO> sToD(List<Faculty> faculties) {
        return faculties.stream()
                .map(this::sToD)
                .toList();
    }


    // POST/PUT  DTO->F
    public Faculty dToS(FacultyFromDTO facultyFromDTO) {
        Faculty faculty = new Faculty();
        if (facultyFromDTO != null) {
            faculty.setName(facultyFromDTO.getName());
            faculty.setColor(facultyFromDTO.getColor());
        }
        return faculty;
    }

}
