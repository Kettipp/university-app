package ua.com.foxminded.unirsityapp.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.com.foxminded.universityapp.model.GroupRepository;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.service.Generate;
import ua.com.foxminded.universityapp.service.impl.ClassGenerator;
import ua.com.foxminded.universityapp.service.impl.GroupServiceImpl;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ClassGenerator.class, GroupServiceImpl.class})
public class ClassGeneratorTest {
    private static final int CLASSES_COUNT = 20;
    @MockBean
    private static GroupRepository groupRepository;

    @Autowired
    private Generate<Class> classGenerate;

    @Autowired
    private ClassGenerator clgn;
    private List<Class> classes;

    @Test
    public void generate_shouldGenerateClasses() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(Teacher.builder().id(1).firstName("Liam").lastName("Smith").username("aaaa").password("1111").build());
        teachers.add(Teacher.builder().id(2).firstName("Liam").lastName("Smith").username("aaaa").password("1111").build());
        Set<Course> courses = new HashSet<>();
        courses.add(Course.builder().id(1).name("Biology").teachers(teachers).build());
        courses.add(Course.builder().id(2).name("Geography").teachers(teachers).build());
        courses.add(Course.builder().id(3).name("Sports").teachers(teachers).build());
        courses.add(Course.builder().id(4).name("Music").teachers(teachers).build());
        courses.add(Course.builder().id(5).name("English").teachers(teachers).build());

        List<Group> groups = new ArrayList<>();
        groups.add(Group.builder().id(1).name("group").courses(courses).build());

        when(groupRepository.findAll()).thenReturn(groups);
        List<Class> classes = classGenerate.generate();

        assertEquals(CLASSES_COUNT, classes.size());

        for (Class clas : classes) {
            assertNotNull(clas.getDay());
            assertNotNull(clas.getTime());
        }
    }

    @Test
    public void generate_shouldAssignToGroupFourDifferentCourseOnADay() {
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            teachers.add(makeTeacher("teacher" + i));
        }
        Set<Course> courses = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            courses.add(makeCourse("Course" + i, teachers.get(i)));
        }
        List<Class> classes = new ArrayList<>();
        classes.add(Class.builder().day(DayOfWeek.MONDAY).time(ClassTime.FIRST).course(Course.builder().name("Course1").build()).build());

        List<Group> groups = new ArrayList<>();
        groups.add(Group.builder().id(1).name("FIRST_G").courses(courses).build());
        groups.add(Group.builder().id(2).name("SECOND_G").courses(courses).build());
        when(groupRepository.findAll()).thenReturn(groups);
        classes = classGenerate.generate();

        Map<Group, List<Class>> collect = classes.stream().collect(Collectors.groupingBy(Class::getGroup));
        for (Map.Entry<Group, List<Class>> groupListEntry : collect.entrySet()) {
            List<Class> classes1 = groupListEntry.getValue();
            Map<DayOfWeek, List<Class>> ofWeekListMap = classes1.stream().collect(Collectors.groupingBy(Class::getDay));
            for (Map.Entry<DayOfWeek, List<Class>> dayOfWeekListEntry : ofWeekListMap.entrySet()) {
                Set<String> classes2 = dayOfWeekListEntry.getValue().stream().map(cl -> cl.getCourse().getName()).collect(Collectors.toSet());
                System.out.println(classes2);
                assertEquals(4, classes2.size());
                Map<ClassTime, List<Class>> classTimeListMap = dayOfWeekListEntry.getValue().stream().collect(Collectors.groupingBy(Class::getTime));
                for (Map.Entry<ClassTime, List<Class>> entry : classTimeListMap.entrySet()) {
                    assertEquals(1, entry.getValue().size());
                }
            }
        }
    }

    @Test
    public void generate_shouldCountOfTeachersClassesOnADay() {
        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            teachers.add(makeTeacher("teacher" + i));
        }
        Set<Course> courses = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            courses.add(makeCourse("Course" + i, teachers.get(i)));
        }

        List<Group> groups = new ArrayList<>();
        groups.add(Group.builder().id(1).name("FIRST_G").courses(courses).build());
        groups.add(Group.builder().id(2).name("SECOND_G").courses(courses).build());
        when(groupRepository.findAll()).thenReturn(groups);
        classes = classGenerate.generate();

        Map<Teacher, List<Class>> collect = classes.stream().collect(Collectors.groupingBy(Class::getTeacher));
        for (Map.Entry<Teacher, List<Class>> groupListEntry : collect.entrySet()) {
            List<Class> classes1 = groupListEntry.getValue();
            Map<DayOfWeek, List<Class>> ofWeekListMap = classes1.stream().collect(Collectors.groupingBy(Class::getDay));
            for (Map.Entry<DayOfWeek, List<Class>> dayOfWeekListEntry : ofWeekListMap.entrySet()) {
                Map<ClassTime, List<Class>> classTimeListMap = dayOfWeekListEntry.getValue().stream().collect(Collectors.groupingBy(Class::getTime));
                for (Map.Entry<ClassTime, List<Class>> entry : classTimeListMap.entrySet()) {
                    assertEquals(1, entry.getValue().size());
                }
            }
        }
    }

    @Test
    public void pickCourse() {
        List<Class> classes = new ArrayList<>();
        classes.add(Class.builder().day(DayOfWeek.MONDAY).time(ClassTime.FIRST).course(Course.builder().name("Biology").build()).build());

        Set<Course> courses = new HashSet<>();
        courses.add(Course.builder().name("Biology").build());
        courses.add(Course.builder().name("Music").build());

        Group group = Group.builder().name("group1").classes(classes).courses(courses).build();
        String expected = "Music";
//        Course course = clgn.pickCourse(group.getCourses().stream().toList(), group, DayOfWeek.MONDAY);

//        String actual = course.getName();
//        assertEquals(expected, actual);
    }

    private Course makeCourse(String value, Teacher t) {
        return Course.builder().name(value).teachers(List.of(t)).build();
    }

    private Teacher makeTeacher(String value) {
        return Teacher.builder().firstName(value).lastName(value).build();
    }

}