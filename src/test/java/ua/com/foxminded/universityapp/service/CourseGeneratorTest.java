package ua.com.foxminded.universityapp.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxminded.universityapp.config.UniversityProperties;
import ua.com.foxminded.universityapp.model.entity.Course;
import ua.com.foxminded.universityapp.service.impl.CourseGenerator;

import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest(classes = {CourseGenerator.class})
@EnableConfigurationProperties(value = UniversityProperties.class)
public class CourseGeneratorTest {
    @Autowired
    private Generate<Course> courseGenerator;

    @Test
    public void generate_shouldGenerateCourses() {
        int expected = 12;

        List<Course> courses = courseGenerator.generate();
        int actual = courses.size();
        assertEquals(expected, actual);
    }
}
