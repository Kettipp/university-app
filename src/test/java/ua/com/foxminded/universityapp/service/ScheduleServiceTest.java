package ua.com.foxminded.universityapp.service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.service.impl.ScheduleService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ScheduleService.class})

public class ScheduleServiceTest {
    private static final int EXPECTED = 5;
    @Autowired
    private ScheduleService scheduleService;

    @Test
    public void generate_shouldGenerateSchedules() {
        Set<Teacher> teachers = new HashSet<>();
        teachers.add(Teacher.builder().id(1).firstName("Liam").lastName("Smith").username("aaaa").password("1111").build());
        teachers.add(Teacher.builder().id(2).firstName("Liam").lastName("Smith").username("aaaa").password("1111").build());
        Set<Course> courses = new HashSet<>();
        courses.add(Course.builder().id(1).name("Biology").teachers(teachers).build());
        courses.add(Course.builder().id(2).name("Geography").teachers(teachers).build());
        courses.add(Course.builder().id(3).name("Sports").teachers(teachers).build());
        courses.add(Course.builder().id(4).name("Music").teachers(teachers).build());
        courses.add(Course.builder().id(5).name("Music1").teachers(teachers).build());
        List<Group> groups = new ArrayList<>();
        groups.add(Group.builder().id(1).name("group").courses(courses).build());
        groups.add(Group.builder().id(2).name("group").courses(courses).build());
        groups.add(Group.builder().id(3).name("group").courses(courses).build());
        groups.add(Group.builder().id(4).name("group").courses(courses).build());
        groups.add(Group.builder().id(5).name("group").courses(courses).build());

        List<Course> courseList = courses.stream().toList();
        List<Class> classes = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Course course = courseList.get(i);
            classes.add(Class.builder()
                    .day(DayOfWeek.MONDAY)
                    .time(ClassTime.FIRST)
                    .group(groups.get(i))
                    .course(course)
                    .teacher(course.getTeachers().stream().findFirst().get())
                    .build());
        }

        List<Schedule> actual = scheduleService.generate(classes);

        System.out.println(actual);
        assertEquals(EXPECTED, actual.size());
    }

    @Test
    public void singleSchedule_shouldCreateEmptySchedule() {
        List<Class> classes = new ArrayList<>();
        List<ClassTime> classTimes = new ArrayList<>();
        classTimes.add(ClassTime.FIRST);

        List<Class> expectedClasses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            expectedClasses.add(Class.builder()
                    .course(Course.builder().name("-").build())
                    .group(Group.builder().name("-").build())
                    .time(ClassTime.FIRST)
                    .build());
        }

        Map<LocalDate, List<Class>> actual = scheduleService.singleSchedule(classes, classTimes);


        for (Map.Entry<LocalDate, List<Class>> entry : actual.entrySet()) {
            List<Class> actualClasses = entry.getValue();
            for (int i =0; i < actualClasses.size(); i++) {
                assertEquals(expectedClasses.get(i), actualClasses.get(i));
            }
        }
    }
}
