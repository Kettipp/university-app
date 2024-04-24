package ua.com.foxminded.universityapp.service;

import ua.com.foxminded.universityapp.model.entity.Class;

import java.util.List;
import java.util.NoSuchElementException;

public interface ClassService extends GenericEntityService<Class> {
    List<Class> getClassByGroupName(String groupName);
    List<Class> getClassByTeacherName(String teacherFirstName, String teacherLastName);
}
