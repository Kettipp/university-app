package ua.com.foxminded.universityapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.service.CourseService;
import ua.com.foxminded.universityapp.service.Generate;
import ua.com.foxminded.universityapp.service.GroupService;

import java.time.DayOfWeek;
import java.util.*;

@Component
public class ClassGenerator implements Generate<Class> {
    private static final int ATTEMPT_COUNTER = 5;
    private final GroupService groupService;
    private final CourseService courseService;
    private final Random random;

    @Autowired
    public ClassGenerator(GroupService groupService, CourseService courseService, Random random) {
        this.groupService = groupService;
        this.courseService = courseService;
        this.random = random;
    }

    @Override
    public List<Class> generate() {
        List<Class> classes = new ArrayList<>();
        List<Course> courses = courseService.getAll();
        List<Group> groups = groupService.getAll();
        for (DayOfWeek day : DayOfWeek.values()) {
            if (day.equals(DayOfWeek.SATURDAY) || day.equals(DayOfWeek.SUNDAY)) continue;
            for (Group group : groups) {
                List<Course> availableCourses = new ArrayList<>(courses);
                for (ClassTime time : ClassTime.values()) {
                    for (int i = 0; i < ATTEMPT_COUNTER; i++) {
                        Optional<Class> clas = generateRandomClass(
                                availableCourses,
                                classes,
                                day,
                                time,
                                group
                        );
                        if (clas.isPresent()) {
                            classes.add(clas.get());
                            break;
                        }
                    }
                }
            }
        }
        return classes;
    }

    private Optional<Class> generateRandomClass(List<Course> availableCourses, List<Class> classes, DayOfWeek day, ClassTime time, Group group) {
        Course course = availableCourses.remove(random.nextInt(availableCourses.size()));
        Set<Teacher> teachers = course.getTeachers();
        Optional<Teacher> first = teachers
                .stream()
                .filter(t -> classes.stream()
                        .noneMatch(cl ->
                                cl.getDay().equals(day)
                                        && cl.getTime().equals(time)
                                        && cl.getTeacher().equals(t)
                        ))
                .findFirst();
        return first.map(teacher -> Class.builder()
                .day(day)
                .time(time)
                .group(group)
                .course(course)
                .teacher(teacher)
                .build());
    }
}
