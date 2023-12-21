package ua.com.foxminded.unirsityapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.model.entity.Course;
import ua.com.foxminded.universityapp.model.entity.Group;
import ua.com.foxminded.universityapp.service.impl.CourseIdDistributorImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {CourseIdDistributorImpl.class})
public class CourseIdDistributorImplTest {

    @Autowired
    private CourseIdDistributorImpl courseIdDistributor;

    @Test
    public void generateCourseDistribution_shouldAssignCoursesToGroup() {
        List<Group> groups = new ArrayList<>();
        groups.add(Group.builder().id(1).name("group").build());

        List<Course> courses = new ArrayList<>();
        courses.add(Course.builder().id(1).name("Biology").build());
        Set<Course> setCourse = new HashSet<>(courses);

        List<Group> expected = new ArrayList<>();
        expected.add(Group.builder().id(1).name("group").courses(setCourse).build());

        List<Group> actual = courseIdDistributor.generateCourseDistribution(groups, courses);
        List<Set<Course>> expectedCourses = expected.stream().map(Group::getCourses).toList();

        actual.forEach(a -> assertTrue(expectedCourses.contains(a.getCourses())));
    }
}
