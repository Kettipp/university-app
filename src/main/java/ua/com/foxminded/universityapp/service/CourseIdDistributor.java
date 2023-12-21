package ua.com.foxminded.universityapp.service;

import ua.com.foxminded.universityapp.model.entity.Course;
import ua.com.foxminded.universityapp.model.entity.Group;

import java.util.List;

public interface CourseIdDistributor {
    List<Group> generateCourseDistribution(List<Group> groups, List<Course> courses);
}
