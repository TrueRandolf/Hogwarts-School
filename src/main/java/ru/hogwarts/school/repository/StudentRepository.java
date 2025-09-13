package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int ageMin, int ageMax);

    @Query(value = "select count(id) from student", nativeQuery = true)
    Integer getNumberOfStudents();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    float getAverageAge();

    @Query(value = "select * from( select * from student order by id desc limit(5) ) as subquery order by id asc", nativeQuery = true)
    List<Student> getLastFiveById();


}
