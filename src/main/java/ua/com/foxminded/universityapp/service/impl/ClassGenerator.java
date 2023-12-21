package ua.com.foxminded.universityapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.model.entity.Class.ClassBuilder;
import ua.com.foxminded.universityapp.service.Generate;

import java.time.DayOfWeek;
import java.util.*;

@Component
public class ClassGenerator implements Generate<Class> {
    private final GroupServiceImpl groupService;

    @Autowired
    public ClassGenerator(GroupServiceImpl groupService) {
        this.groupService = groupService;
    }

    private Course pickCourse(HashMap<Course, Integer> storage) {
        storage.forEach((k, v) -> {
            if (v == 2) {
                storage.remove(k);
            }
        });
        return storage.keySet().stream().toList().get(new Random().nextInt(storage.size()));
    }

    @Override
    @Transactional
    public List<Class> generate() {
        List<Group> groups = groupService.getAll();
        List<Class> classes = new ArrayList<>();
        for (Group group : groups) {
            List<ClassBuilder> classBuilders =
                    Arrays.stream(ClassTime.values()).map(ct ->  Class.builder().group(group).time(ct)).toList();

            HashMap<Course, Integer> storage = new HashMap<>();
            group.getCourses().forEach(c -> storage.put(c, 0));
            for (DayOfWeek day : DayOfWeek.values()) {
                if(day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                    classes.addAll(
                            classBuilders
                                    .stream()
                                    .map(cb -> {
                                        Course course = pickCourse(storage);
                                        List<Teacher> teachers = course.getTeachers();
                                        return cb
                                                .day(day)
                                                .course(course)
                                                .teacher(peekTeacher(teachers))
                                                .build();
                                    })
                                    .toList()
                    );
                }
            }
        }
        return classes;
    }

    private Teacher peekTeacher(List<Teacher> teachers) {
        return teachers.stream().findAny().get();
    }


}
