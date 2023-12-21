package ua.com.foxminded.universityapp.service.impl;

import org.springframework.stereotype.Component;
import ua.com.foxminded.universityapp.model.entity.Course;
import ua.com.foxminded.universityapp.model.entity.Group;
import ua.com.foxminded.universityapp.service.CourseIdDistributor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Component
public class CourseIdDistributorImpl implements CourseIdDistributor {
    @Override
    public List<Group> generateCourseDistribution(List<Group> groups, List<Course> courses) {
        for (Group group : groups) {
            Set<Course> courseSet = new HashSet<>(courses);
            group.setCourses(courseSet);
        }
        return groups;
    }
}
