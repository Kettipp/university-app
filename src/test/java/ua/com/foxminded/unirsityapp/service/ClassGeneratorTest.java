package ua.com.foxminded.unirsityapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.shaded.org.bouncycastle.math.ec.custom.sec.SecT113Field;
import ua.com.foxminded.universityapp.model.GroupRepository;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.service.Generate;
import ua.com.foxminded.universityapp.service.impl.ClassGenerator;
import ua.com.foxminded.universityapp.service.impl.GroupServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ClassGenerator.class, GroupServiceImpl.class})
public class ClassGeneratorTest {
    private static final int CLASSES_COUNT = 20;
    @MockBean
    private GroupRepository groupRepository;

    @Autowired
    private Generate<Class> classGenerate;

    @Test
    public void generate_shouldGenerateClasses() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(Teacher.builder().id(1).firstName("Liam").lastName("Smith").login("aaaa").password("1111").build());
        teachers.add(Teacher.builder().id(2).firstName("Liam").lastName("Smith").login("aaaa").password("1111").build());
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
}
