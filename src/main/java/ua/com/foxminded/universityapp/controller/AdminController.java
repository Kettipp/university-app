package ua.com.foxminded.universityapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.service.ClassService;
import ua.com.foxminded.universityapp.service.CourseService;
import ua.com.foxminded.universityapp.service.GroupService;
import ua.com.foxminded.universityapp.service.UserService;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class AdminController {
    private static final String ADMIN = "Admin";
    private static final String STUDENT = "Student";
    private static final String TEACHER = "Teacher";
    private final UserService<User> userService;
    private final GroupService groupService;
    private final ClassService classService;
    private final CourseService courseService;

    public AdminController(UserService<User> userService, GroupService groupService,
                           ClassService classService, CourseService courseService) {
        this.userService = userService;
        this.groupService = groupService;
        this.classService = classService;
        this.courseService = courseService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<User> users = userService.getAll();
        List<String> usersRole = users.stream().map(User::getRole).toList();
        model.addAttribute("role", usersRole);
        return "redirect:/schedule/all";
    }

    @GetMapping("/createNewCourse")
    public String createCoursePage(Model model) {
        appendModelWithData(model);
        return "createNewCourse";
    }

    @PostMapping("/createNewCourse")
    public String createCourse(Model model, @RequestParam String courseName) {
        appendModelWithData(model);
        courseService.add(Course.builder().name(courseName).build());
        return "createNewCourse";
    }

    @GetMapping("/createNewUser")
    public String createUserPage(Model model) {
        appendModelWithData(model);
        return "createNewUser";
    }

    @PostMapping("/createNewUser")
    public String createUser(@ModelAttribute UserDTO user, Model model) {
        appendModelWithData(model);

        User.UserBuilder userBuilder = switch (user.getRole()) {
            case STUDENT -> Student.builder();
            case TEACHER -> Teacher.builder();
            case ADMIN -> Admin.builder();
            default -> throw new IllegalStateException("Unexpected value: " + user.getRole());
        };
        userService.add(userBuilder
                .role(user.getRole())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .build());
        return "createNewUser";
    }

    @GetMapping("/editSchedule")
    public String editSchedule(Model model) {
        appendModelWithData(model);

        model.addAttribute("days", DayOfWeek.values());
        model.addAttribute("time", ClassTime.values());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("teachers", userService.getTeachers());
        return "editSchedule";
    }

    @PostMapping("/editSchedule/delete")
    public String deleteClass(@ModelAttribute ClassDTO clas, Model model) {
        appendModelWithData(model);

        model.addAttribute("days", DayOfWeek.values());
        model.addAttribute("time", ClassTime.values());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("courses", courseService.getAll());
        Optional<Long> idToDelete = classService.getAll().stream().filter(cl -> {
                    boolean day = cl.getDay().equals(clas.getDay());
                    boolean time = cl.getTime().equals(clas.getTime());
                    boolean group = cl.getGroup().getName().equals(clas.getGroupName());
                    boolean course = cl.getCourse().getName().equals(clas.getCourseName());
                    return day && time && group && course;
                })
                .findAny()
                .map(Class::getId);
        if (idToDelete.isPresent()){
            classService.deleteById(idToDelete.get());
        } else {
            log.warn("Nothing found for deleting, dto: {}", clas);
        }
        return "editSchedule";
    }

    @PostMapping("/editSchedule/add")
    public String addClass(@ModelAttribute ClassDTO clas, Model model) {
        model.addAttribute("days", DayOfWeek.values());
        model.addAttribute("time", ClassTime.values());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("teachers", userService.getTeachers());

        classService.add(Class.builder()
                .day(clas.getDay())
                .time(clas.getTime())
                .group(groupService.getByName(clas.getGroupName()))
                .course(courseService.getByName(clas.getCourseName()))
                .teacher((Teacher) userService.getByTeacherName(clas.getTeacherFirstname(), clas.getTeacherLastname()))
                .build());
        return "editSchedule";
    }
    private void appendModelWithData(Model model) {
        List<Teacher> teachers = userService.getTeachers();
        model.addAttribute("teachers", teachers);
        List<Group> groups = groupService.getAll();
        model.addAttribute("groups", groups);
    }
}
