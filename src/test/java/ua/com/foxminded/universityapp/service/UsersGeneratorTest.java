package ua.com.foxminded.universityapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ua.com.foxminded.universityapp.config.UniversityProperties;
import ua.com.foxminded.universityapp.repository.CourseRepository;
import ua.com.foxminded.universityapp.repository.GroupRepository;
import ua.com.foxminded.universityapp.repository.UserRepository;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.service.impl.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UsersGenerator.class, GroupServiceImpl.class, CourseServiceImpl.class, UserServiceImpl.class})
@EnableConfigurationProperties(value = UniversityProperties.class)
@ActiveProfiles("test")
public class UsersGeneratorTest {
    @MockBean
    private Random random;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private GroupRepository groupRepository;
    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private UserRepository userRepository;


    @Autowired
    private Generate<User> usersGenerator;

    @Test
    public void generate_shouldGenerateUsers() {
        List<Group> group = new ArrayList<>();
        group.add(Group.builder().id(1).name("A").build());
        Set<Course> courses = new HashSet<>();
        courses.add(Course.builder().id(1).name("A").build());
        courses.add(Course.builder().id(2).name("A").build());
        courses.add(Course.builder().id(3).name("A").build());
        List<Course> c = new ArrayList<>(courses);

        when(groupRepository.findAll()).thenReturn(group);
        when(courseRepository.findAll()).thenReturn(c);

        List<User> expected = new ArrayList<>();
        expected.add(Student.builder().firstName("Liam").lastName("Smith").username("aaaaa").password("111").group(group.get(0)).build());
        expected.add(Teacher.builder().firstName("Olivia").lastName("Johnson").username("aaaaa").password("111").courses(courses).build());
        expected.add(Admin.builder().firstName("admin").lastName("admin").username("universityAdmin@com.ua").password("11111111").build());
        List<String> expectedNames = expected.stream().map(User::getFirstName).toList();


        List<User> actual = usersGenerator.generate();
        actual.forEach(u -> assertTrue(expectedNames.contains(u.getFirstName())));
    }

}
