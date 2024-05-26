package ua.com.foxminded.universityapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.repository.ClassRepository;
import ua.com.foxminded.universityapp.repository.CourseRepository;
import ua.com.foxminded.universityapp.repository.GroupRepository;
import ua.com.foxminded.universityapp.repository.UserRepository;
import ua.com.foxminded.universityapp.service.impl.ClassGenerator;
import ua.com.foxminded.universityapp.service.impl.CourseGenerator;
import ua.com.foxminded.universityapp.service.impl.UsersGenerator;

import java.time.DayOfWeek;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MockBean({ClassGenerator.class, CourseGenerator.class, UsersGenerator.class})
public class ClassServiceTest {

    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassService classService;

    @Test
    public void changeClass_shouldThrowException_whenReceiveClassWhichAlreadyExist() {
        Group group = Group.builder().name("gr1").build();
        Group group2 = Group.builder().name("gr2").build();
        groupRepository.saveAndFlush(group);
        groupRepository.saveAndFlush(group2);
        Course course = Course.builder().name("course").build();
        courseRepository.save(course);
        Teacher teacher = Teacher.builder().firstName("Liam").lastName("Smith").username("aaaa").password("1111").build();
        userRepository.save(teacher);
        Class class1 = Class.builder().day(DayOfWeek.MONDAY).time(ClassTime.FIRST).group(group).course(course).teacher(teacher).build();
        Class class2 = Class.builder().day(DayOfWeek.MONDAY).time(ClassTime.SECOND).group(group).course(course).teacher(teacher).build();
        Class class3 = Class.builder().day(DayOfWeek.MONDAY).time(ClassTime.THIRD).group(group).course(course).teacher(teacher).build();
        Class class4 = Class.builder().day(DayOfWeek.MONDAY).time(ClassTime.FOURTH).group(group).course(course).teacher(teacher).build();
        Class class5 = Class.builder().day(DayOfWeek.MONDAY).time(ClassTime.FOURTH).group(group2).course(course).teacher(teacher).build();
        classRepository.saveAllAndFlush(List.of(class1, class2, class3, class4, class5));
        assertThrows(DataIntegrityViolationException.class, () -> classService.changeClass(
                ClassDTO.builder()
                        .id(class4.getId())
                        .day(DayOfWeek.MONDAY)
                        .time(ClassTime.FOURTH)
                        .groupId(group2.getId())
                        .courseId(course.getId())
                        .teacherId(teacher.getId())
                        .build()
        ));
    }
}
