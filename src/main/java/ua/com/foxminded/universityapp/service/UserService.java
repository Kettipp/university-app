package ua.com.foxminded.universityapp.service;

import org.springframework.data.repository.query.Param;
import ua.com.foxminded.universityapp.model.entity.Teacher;
import ua.com.foxminded.universityapp.model.entity.User;

import java.util.List;

public interface UserService<T> extends GenericEntityService<T> {
    List<Teacher> getTeachers();
    User getByTeacherName(String firstName, String lastName);
}
