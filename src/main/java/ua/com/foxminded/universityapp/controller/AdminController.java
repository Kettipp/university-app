package ua.com.foxminded.universityapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.service.ClassService;
import ua.com.foxminded.universityapp.service.CourseService;
import ua.com.foxminded.universityapp.service.GroupService;
import ua.com.foxminded.universityapp.service.UserService;
import ua.com.foxminded.universityapp.service.impl.TeacherService;

import java.time.DayOfWeek;
import java.util.List;

@Controller
@Slf4j
public class AdminController {
    private static final String ADMIN = "Admin";
    private static final String STUDENT = "Student";
    private static final String TEACHER = "Teacher";
    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE = "Group or Teacher already have class at this time";
    private final UserService<User> userService;
    private final TeacherService teacherService;
    private final GroupService groupService;
    private final ClassService classService;
    private final CourseService courseService;

    public AdminController(UserService<User> userService, TeacherService teacherService, GroupService groupService,
                           ClassService classService, CourseService courseService) {
        this.userService = userService;
        this.teacherService = teacherService;
        this.groupService = groupService;
        this.classService = classService;
        this.courseService = courseService;
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
        model.addAttribute("message", SUCCESS_MESSAGE);
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
        model.addAttribute("message", SUCCESS_MESSAGE);
        return "createNewUser";
    }

    @GetMapping("/editSchedule/{id}")
    public String editSchedule(Model model, @PathVariable long id) {
        appendModelWithData(model);
        Class clas = classService.getById(id);
        model.addAttribute("clas", clas);
        return "editSchedule";
    }

    @PostMapping("/editSchedule/delete")
    public String deleteClass(@ModelAttribute Class clas, Model model) {
        appendModelWithData(model);
        classService.deleteById(clas.getId());
        model.addAttribute("message", SUCCESS_MESSAGE);
        return "redirect:/schedule/all";
    }

    @PostMapping("/editSchedule/change")
    public String changeClass(@ModelAttribute ClassDTO clas, Model model, RedirectAttributes redirectAttributes) {
        appendModelWithData(model);
        try {
            classService.changeClass(clas);
            redirectAttributes.addFlashAttribute("message", SUCCESS_MESSAGE);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", ERROR_MESSAGE);
            return "redirect:/editSchedule/" + clas.getId();
        }
        return "redirect:/schedule/all";
    }

    @GetMapping("/addClass")
    public String addClas(Model model) {
        appendModelWithData(model);
        return "addClass";
    }

    @PostMapping("/addClass")
    public String addClass(@ModelAttribute ClassDTO clas, Model model) {
        appendModelWithData(model);
        try {
            classService.add(Class.builder()
                    .day(clas.getDay())
                    .time(clas.getTime())
                    .group(groupService.getById(clas.getGroupId()))
                    .course(courseService.getById(clas.getCourseId()))
                    .teacher(teacherService.getById(clas.getTeacherId()))
                    .build());
            model.addAttribute("message", SUCCESS_MESSAGE);
        } catch (Exception e){
            model.addAttribute("message", ERROR_MESSAGE);
        }
        return "addClass";
    }

    private void appendModelWithData(Model model) {
        List<Teacher> teachers = userService.getTeachers();
        model.addAttribute("teachers", teachers);
        List<Group> groups = groupService.getAll();
        model.addAttribute("groups", groups);
        model.addAttribute("days", DayOfWeek.values());
        model.addAttribute("time", ClassTime.values());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("courses", courseService.getAll());
        model.addAttribute("teachers", userService.getTeachers());
    }
}
