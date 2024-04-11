package ua.com.foxminded.universityapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.universityapp.model.entity.Course;
import ua.com.foxminded.universityapp.model.entity.Group;
import ua.com.foxminded.universityapp.model.entity.User;
import ua.com.foxminded.universityapp.service.impl.ClassGenerator;
import ua.com.foxminded.universityapp.service.impl.CourseGenerator;
import ua.com.foxminded.universityapp.service.impl.GroupGenerator;
import ua.com.foxminded.universityapp.service.impl.UsersGenerator;

import java.util.List;

@Component
public class Initialization {
    private final UsersGenerator usersGenerator;
    private final GroupGenerator groupGenerator;
    private final CourseGenerator courseGenerator;
    private final ClassGenerator classGenerator;
    private final UserService<User> userService;
    private final GroupService groupService;
    private final CourseService courseService;
    private final ClassService classService;
    private final CourseIdDistributor courseIdDistributor;

    @Autowired
    public Initialization(UsersGenerator usersGenerator, GroupGenerator groupGenerator,
                          CourseGenerator courseGenerator, ClassGenerator classGenerator,
                          UserService<User> userService, GroupService groupService,
                          CourseService courseService, ClassService classService, CourseIdDistributor courseIdDistributor) {
        this.usersGenerator = usersGenerator;
        this.groupGenerator = groupGenerator;
        this.courseGenerator = courseGenerator;
        this.classGenerator = classGenerator;
        this.userService = userService;
        this.groupService = groupService;
        this.courseService = courseService;
        this.classService = classService;
        this.courseIdDistributor = courseIdDistributor;
    }

    public void init() {
        List<Course> courses = courseGenerator.generate();
        courseService.addAll(courses);
        List<Group> groups = courseIdDistributor.generateCourseDistribution(groupGenerator.generate(), courses);
        groupService.addAll(groups);
        userService.addAll(usersGenerator.generate());
        classService.addAll(classGenerator.generate());
    }
}
