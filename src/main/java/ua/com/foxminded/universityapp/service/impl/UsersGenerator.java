package ua.com.foxminded.universityapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.universityapp.config.UniversityProperties;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.service.Generate;
import ua.com.foxminded.universityapp.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Component
public class UsersGenerator implements Generate<User> {
    private static final String FORMAT = "%s@com.ua";
    private static final  int ALPHABET_SIZE = 26;
    private  final  int loginSize;
    private final  int passwordSize;
    private final  int passwordSymbolLimit;
    private final  int studentsCount;
    private final  int teachersCount;
    private final  String adminFirstName;
    private final  String adminLastName;
    private final UniversityProperties properties;
    private final GroupServiceImpl groupService;
    private final CourseServiceImpl courseService;
    private final UserService<Teacher> userService;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    @Autowired
    public UsersGenerator(@Value("${university.loginSize}") int loginSize, @Value("${university.passwordSize}") int passwordSize,
                          @Value("${university.passwordSymbolLimit}") int passwordSymbolLimit, @Value("${university.studentsCount}") int studentsCount,
                          @Value("${university.teachersCount}") int teachersCount, @Value("${university.adminFirstName}") String adminFirstName,
                          @Value("${university.adminLastName}") String adminLastName, UniversityProperties properties,
                          GroupServiceImpl groupService, CourseServiceImpl courseService,
                          UserService userService, PasswordEncoder passwordEncoder) {
        this.loginSize = loginSize;
        this.passwordSize = passwordSize;
        this.passwordSymbolLimit = passwordSymbolLimit;
        this.studentsCount = studentsCount;
        this.teachersCount = teachersCount;
        this.adminFirstName = adminFirstName;
        this.adminLastName = adminLastName;
        this.properties = properties;
        this.groupService = groupService;
        this.courseService = courseService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> generate() {
        List<User> users = new ArrayList<>();
        List<Student> students = generateStudents(generateUser(studentsCount));
        List<Teacher> teachers = generateTeachers(generateUser(teachersCount));
        Admin admin = generateAdmin();
        users.addAll(students);
        users.addAll(teachers);
        users.add(admin);
        return users;
    }

    private List<Student> generateStudents(List<User> users) {
        List<Group> groups = groupService.getAll();
        return users.stream().map(
                user -> {
                    user = Student.builder()
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .build();
                    Student student = (Student) user;
                        student.setGroup(groups.get(random.nextInt(groups.size())));
                    return student;
                }
        ).collect(Collectors.toList());
    }

    private List<Teacher> generateTeachers(List<User> users) {
        List<Course> courses = courseService.getAll();
        ArrayList<Course> courseStore = new ArrayList<>(courses);
        List<Teacher> teachers = users.stream().map(
                user -> {
                    user = Teacher.builder()
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .build();
                    Teacher teacher = (Teacher) user;
                    Set<Course> coursesSet = pickTwoCourses(courseStore, courses);
                    teacher.setCourses(coursesSet);
                    return teacher;
                }
        ).toList();
        for(Teacher teacher : teachers) {
            List<String> coursesName = teacher.getCourses().stream().map(Course::getName).toList();
            courses.stream()
                    .filter(course -> coursesName.contains(course.getName()))
                    .forEach(course -> course.getTeachers().add(teacher));
        }
        userService.addAll(teachers);
        return teachers;
    }

    private Set<Course> pickTwoCourses(List<Course> courseStore, List<Course> courses) {
        if (courseStore.size() >= 2) {
            return Set.of(
                    courseStore.remove(0),
                    courseStore.remove(0)
            );
        } else {
            int index = random.nextInt(0, courses.size() - 1);
            int index2;
            do {
                index2 = random.nextInt(0, courses.size() - 1);
            } while (index2 == index);
            return Set.of(
                    courses.get(index),
                    courses.get(index2)
            );
        }
    }

    private Admin generateAdmin() {
        return Admin.builder()
                .firstName(adminFirstName)
                .lastName(adminLastName)
                .username(loginGenerate())
                .password(passwordGenerate())
                .build();
    }

    private List<User> generateUser(int count) {
        List<User> users = new ArrayList<>();
        List<String> firstName = properties.getFirstNames();
        List<String> lastNames = properties.getLastNames();
        for (int i = 0; i < count; i++) {
            String first = firstName.get(random.nextInt(firstName.size()));
            String last = lastNames.get(random.nextInt(lastNames.size()));
            User user = User.builder()
                    .firstName(first)
                    .lastName(last)
                    .username(loginGenerate())
                    .password(passwordGenerate())
                    .build();
            users.add(user);
        }
        return users;
    }

    private String loginGenerate() {
        StringJoiner joiner = new StringJoiner("");
        for (int i = 0; i < loginSize; i++) {
            joiner.add(String.valueOf((char) ('a' + random.nextInt(ALPHABET_SIZE))));
        }
        return String.format(FORMAT, joiner);
    }

    private String passwordGenerate() {
//        StringJoiner password = new StringJoiner("");
//        for (int i = 0; i < passwordSize; i++) {
//            password.add(String.valueOf(random.nextInt(passwordSymbolLimit)));
//        }
        String password = "00000000";
        return passwordEncoder.encode(password);
    }
}
