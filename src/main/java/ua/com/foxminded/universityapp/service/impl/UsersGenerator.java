package ua.com.foxminded.universityapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final  String adminLogin;
    private final  String adminPassword;
    private final UniversityProperties properties;
    private final GroupServiceImpl groupService;
    private final CourseServiceImpl courseService;
    private final UserService<Teacher> userService;
    private final Random random = new Random();

    @Autowired
    public UsersGenerator(@Value("${university.loginSize}") int loginSize, @Value("${university.passwordSize}") int passwordSize,
                          @Value("${university.passwordSymbolLimit}") int passwordSymbolLimit, @Value("${university.studentsCount}") int studentsCount,
                          @Value("${university.teachersCount}") int teachersCount, @Value("${university.adminFirstName}") String adminFirstName,
                          @Value("${university.adminLastName}") String adminLastName, @Value("${university.adminLogin}") String adminLogin,
                          @Value("${university.adminPassword}") String adminPassword,
                          UniversityProperties properties,
                          GroupServiceImpl groupService,
                          CourseServiceImpl courseService, UserService userService) {
        this.loginSize = loginSize;
        this.passwordSize = passwordSize;
        this.passwordSymbolLimit = passwordSymbolLimit;
        this.studentsCount = studentsCount;
        this.teachersCount = teachersCount;
        this.adminFirstName = adminFirstName;
        this.adminLastName = adminLastName;
        this.adminLogin = adminLogin;
        this.adminPassword = adminPassword;
        this.properties = properties;
        this.groupService = groupService;
        this.courseService = courseService;
        this.userService = userService;
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
                            .login(user.getLogin())
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
                            .login(user.getLogin())
                            .password(user.getPassword())
                            .build();
                    Teacher teacher = (Teacher) user;
                    Set<Course> coursesSet = pick2courses(courseStore, courses);
                    teacher.setCourses(coursesSet);
                    return teacher;
                }
        ).toList();
        for(Teacher teacher : teachers) {
            List<String> courses1 = teacher.getCourses().stream().map(Course::getName).toList();
            courses.stream()
                    .filter(course -> courses1.contains(course.getName()))
                    .forEach(course -> course.getTeachers().add(teacher));
        }
        userService.addAll(teachers);
        return teachers;
    }

    private Set<Course> pick2courses(List<Course> courseStore, List<Course> courses) {
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
                .login(adminLogin)
                .password(adminPassword)
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
                    .login(loginGenerate())
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
        StringJoiner password = new StringJoiner("");
        for (int i = 0; i < passwordSize; i++) {
            password.add(String.valueOf(random.nextInt(passwordSymbolLimit)));
        }
        return password.toString();
    }
}
