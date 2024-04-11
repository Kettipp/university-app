package ua.com.foxminded.universityapp.service;

import org.springframework.data.repository.query.Param;
import ua.com.foxminded.universityapp.model.entity.Course;
import ua.com.foxminded.universityapp.model.entity.User;

public interface CourseService extends GenericEntityService<Course> {
    Course getByName(String courseName);
}
