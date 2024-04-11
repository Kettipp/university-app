package ua.com.foxminded.universityapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.universityapp.config.UniversityProperties;
import ua.com.foxminded.universityapp.model.entity.Course;
import ua.com.foxminded.universityapp.model.entity.Group;
import ua.com.foxminded.universityapp.service.Generate;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
@Component
public class CourseGenerator implements Generate<Course> {
    private final UniversityProperties properties;

    @Autowired
    public CourseGenerator(UniversityProperties properties) {
        this.properties = properties;
    }

    @Override
    public List<Course> generate() {
        List<Course> courses = new ArrayList<>();
        List<String> courseNames = properties.getCourseNames();
        for(String name : courseNames) {
            Course course = Course.builder().name(name).build();
            courses.add(course);
        }
        return courses;
    }
}
