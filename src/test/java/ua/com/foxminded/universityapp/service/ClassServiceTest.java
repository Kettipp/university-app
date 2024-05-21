package ua.com.foxminded.universityapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.com.foxminded.universityapp.TestConfig;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.repository.ClassRepository;
import ua.com.foxminded.universityapp.repository.CourseRepository;
import ua.com.foxminded.universityapp.repository.GroupRepository;
import ua.com.foxminded.universityapp.repository.UserRepository;
import ua.com.foxminded.universityapp.service.impl.ClassGenerator;
import ua.com.foxminded.universityapp.service.impl.CourseGenerator;
import ua.com.foxminded.universityapp.service.impl.GroupGenerator;
import ua.com.foxminded.universityapp.service.impl.UsersGenerator;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClassServiceTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);

    }

    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ClassService classService;
    @MockBean
    private ClassGenerator classGenerator;
    @MockBean
    private CourseGenerator courseGenerator;
    @MockBean
    private GroupGenerator groupGenerator;
    @MockBean
    private UsersGenerator usersGenerator;
    @MockBean
    private Random random;


    @Test
    public void changeClass_shouldThrowException_whenReceiveClassWhichAlreadyExist() {
        Group group = Group.builder().name("gr1").build();
        groupRepository.saveAndFlush(group);
        Class class1 = Class.builder().day(DayOfWeek.MONDAY).time(ClassTime.FIRST).group(group).build();
        Class class2 = Class.builder().day(DayOfWeek.MONDAY).time(ClassTime.SECOND).group(group).build();
        Class class3 = Class.builder().day(DayOfWeek.MONDAY).time(ClassTime.THIRD).group(group).build();
        Class class4 = Class.builder().day(DayOfWeek.MONDAY).time(ClassTime.FOURTH).group(group).build();
        classRepository.saveAllAndFlush(List.of(class1, class2, class3, class4));
        assertThrows(Exception.class, () -> classService.changeClass(
                ClassDTO.builder()
                        .id(class4.getId())
                        .day(DayOfWeek.MONDAY)
                        .time(ClassTime.FIRST)
                        .groupId(group.getId())
                        .build()
        ));
    }
}
