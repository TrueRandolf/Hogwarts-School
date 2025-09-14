package ru.hogwarts.school.dto;

import ru.hogwarts.school.model.Avatar;

import java.util.List;

public class AvatarMapper {

    public AvatarMapper() {
    }

    public AvatarToDTO aToD(Avatar avatar) {
        AvatarToDTO avatarToDTO = new AvatarToDTO();
        if (avatar != null) {
            avatarToDTO.setAvatarId(avatar.getId());
            avatarToDTO.setFilePath(avatar.getFilePath());
            avatarToDTO.setMediaType(avatar.getMediaType());
            avatarToDTO.setFilePath(avatar.getFilePath());
            avatarToDTO.setFileSize(avatar.getFileSize());
        }

        if (avatar != null && avatar.getStudent() != null) {
            avatarToDTO.setStudentId(avatar.getStudent().getId());
            avatarToDTO.setName(avatar.getStudent().getName());
            avatarToDTO.setAge(avatar.getStudent().getAge());
            avatarToDTO.setFacultyId(avatar.getStudent().getFaculty().getId());
            avatarToDTO.setFacultyName(avatar.getStudent().getFaculty().getName());
        }
        return avatarToDTO;
    }

    public List<AvatarToDTO> aToD(List<Avatar> avatars) {
        return avatars.stream()
                .map(this::aToD)
                .toList();
    }

}
