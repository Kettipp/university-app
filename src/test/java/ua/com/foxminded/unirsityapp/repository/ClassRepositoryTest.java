package ua.com.foxminded.unirsityapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.com.foxminded.unirsityapp.TestConfig;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.repository.ClassRepository;
import ua.com.foxminded.universityapp.repository.CourseRepository;
import ua.com.foxminded.universityapp.repository.GroupRepository;
import ua.com.foxminded.universityapp.repository.UserRepository;

import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers(disabledWithoutDocker = true)
public class ClassRepositoryTest {

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

    @Test
    public void save_shouldThrowException_whenReceiveClassWhichAlreadyExist() {
        Class clas = Class.builder()
                .day(DayOfWeek.MONDAY)
                .time(ClassTime.FIRST)
                .group(Group.builder().id(1).name("group").build())
                .course(Course.builder().id(1).name("course").build())
                .teacher(Teacher.builder().id(1).firstName("Liam").lastName("Smith").username("aaaa").password("1111").build())
                .build();
        classRepository.save(clas);
        assertThrows(IllegalArgumentException.class, () -> classRepository.save(clas));
    }

}
