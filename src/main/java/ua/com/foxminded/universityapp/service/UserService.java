package ua.com.foxminded.universityapp.service;

import ua.com.foxminded.universityapp.model.entity.Teacher;

import java.util.List;

public interface UserService<T> extends GenericEntityService<T> {
    List<Teacher> getTeachers();
}
