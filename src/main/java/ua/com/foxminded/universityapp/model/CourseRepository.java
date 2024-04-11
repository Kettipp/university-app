package ua.com.foxminded.universityapp.model;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.universityapp.model.entity.Course;
import ua.com.foxminded.universityapp.model.entity.Group;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c WHERE c.name = :name")
    Course findByName(@Param("name") String courseName);
}
