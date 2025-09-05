package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    List<Faculty> findByColor(String color);

    @Query("SELECT f FROM Faculty f WHERE f.name ILIKE %:part% OR f.color ILIKE %:part%")
    List<Faculty> searchByPart(@Param("part") String part);

}
