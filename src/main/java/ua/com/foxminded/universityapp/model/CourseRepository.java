package ua.com.foxminded.universityapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.universityapp.model.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
