package ua.com.foxminded.universityapp.service;

import org.springframework.data.repository.query.Param;
import ua.com.foxminded.universityapp.model.entity.Class;

import java.util.List;

public interface ClassService extends GenericEntityService<Class> {
    List<Class> getClassByGroupName(String groupName);
    List<Class> getClassByTeacherName(String teacherFirstName, String teacherLastName);
}
