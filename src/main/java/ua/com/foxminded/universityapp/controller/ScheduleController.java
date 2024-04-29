package ua.com.foxminded.universityapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.service.ClassService;
import ua.com.foxminded.universityapp.service.GroupService;
import ua.com.foxminded.universityapp.service.UserService;
import ua.com.foxminded.universityapp.service.impl.ScheduleService;
import ua.com.foxminded.universityapp.service.impl.TeacherService;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/schedule")
@Slf4j
public class ScheduleController {
    private final TeacherService teacherService;
    private final GroupService groupService;
    private final ClassService classService;
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController( TeacherService teacherService, ClassService classService,
                              GroupService groupService, ScheduleService scheduleService) {
        this.teacherService = teacherService;
        this.classService = classService;
        this.groupService = groupService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/all")
    public String schedulePage(Model model) {
        appendModelWithData(model);
        List<Schedule> schedule = scheduleService.generate(classService.getAll());
        model.addAttribute("schedules", schedule);
        List<String> timeSlots = Arrays.stream(ClassTime.values()).map(ClassTime::getTimeSlot).toList();
        model.addAttribute("times", timeSlots);
        return "schedule";
    }

    @GetMapping("/group/{groupName}")
    public String groupSchedule(@PathVariable String groupName, Model model) {
        appendModelWithData(model);
        List<ClassTime> classTime = Arrays.stream(ClassTime.values()).toList();
        List<String> timeSlots = classTime.stream().map(ClassTime::getTimeSlot).toList();
        model.addAttribute("times", timeSlots);
        List<Class> classes = classService.getClassByGroupName(groupName);
        Map<LocalDate, List<Class>> singleSchedule = scheduleService.singleSchedule(classes, classTime);
        model.addAttribute("schedule", singleSchedule);
        return "groupSchedule";
    }

    @GetMapping("/teacher/{teacherFirstName}/{teacherLastName}")
    public String teacherSchedule(@PathVariable String teacherFirstName, @PathVariable String teacherLastName, Model model) {
        appendModelWithData(model);
        List<ClassTime> classTime = Arrays.stream(ClassTime.values()).toList();
        model.addAttribute("times", classTime);
        List<Class> classes = classService.getClassByTeacherName(teacherFirstName, teacherLastName);
        Map<LocalDate, List<Class>> singleSchedule = scheduleService.singleSchedule(classes, classTime);
        model.addAttribute("schedule", singleSchedule);
        return "teacherSchedule";
    }

    private void appendModelWithData(Model model) {
        List<Teacher> teachers = teacherService.getTeachers();
        model.addAttribute("teachers", teachers);
        List<Group> groups = groupService.getAll();
        model.addAttribute("groups", groups);
    }
}
