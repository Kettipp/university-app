package ua.com.foxminded.universityapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.universityapp.model.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
