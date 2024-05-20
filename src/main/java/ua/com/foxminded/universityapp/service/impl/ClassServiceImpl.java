package ua.com.foxminded.universityapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.repository.ClassRepository;
import ua.com.foxminded.universityapp.service.ClassService;
import ua.com.foxminded.universityapp.service.CourseService;
import ua.com.foxminded.universityapp.service.GroupService;

import java.util.List;

@Service
@Slf4j
public class ClassServiceImpl implements ClassService {
    private final ClassRepository repository;
    private final GroupService groupService;
    private final CourseService courseService;
    private final TeacherService teacherService;

    @Autowired
    public ClassServiceImpl(ClassRepository repository, GroupService groupService, CourseService courseService, TeacherService teacherService) {
        this.repository = repository;
        this.groupService = groupService;
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @Override
    public void addAll(List<Class> classes) {
        repository.saveAll(classes);
    }

    @Transactional
    @Override
    public void add(Class clas) {
        repository.save(clas);
    }

    @Override
    public List<Class> getAll() {
        return repository.findAll();
    }

    @Override
    public Class getById(long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Class> getClassByGroupName(String groupName) {
        return repository.findClassByGroupName(groupName);
    }

    @Override
    public List<Class> getClassByTeacherName(String teacherFirstName, String teacherLastName) {
        return repository.findClassByTeacherName(teacherFirstName, teacherLastName);
    }

    @Transactional
    public void changeClass(ClassDTO classDTO) {
        Class clas = getById(classDTO.getId());
        clas.setDay(classDTO.getDay());
        clas.setTime(classDTO.getTime());
        clas.setGroup(groupService.getById(classDTO.getGroupId()));
        clas.setCourse(courseService.getById(classDTO.getCourseId()));
        clas.setTeacher(teacherService.getById(classDTO.getTeacherId()));
        add(clas);
    }
}
