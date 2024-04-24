package ua.com.foxminded.universityapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.universityapp.model.repository.ClassRepository;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.service.ClassService;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClassServiceImpl implements ClassService {
    private final ClassRepository repository;

    @Autowired
    public ClassServiceImpl(ClassRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addAll(List<Class> classes) {
        repository.saveAll(classes);
    }

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
        return  repository.findById(id).orElseThrow();
    }
    @Override
    public void deleteById(long id) {
        log.info("Delete class with id: {}", id);
        repository.deleteById(id);
        repository.flush();
    }

    @Override
    public List<Class> getClassByGroupName(String groupName) {
        return repository.findClassByGroupName(groupName);
    }

    @Override
    public List<Class> getClassByTeacherName(String teacherFirstName, String teacherLastName) {
        return  repository.findClassByTeacherName(teacherFirstName, teacherLastName);
    }
}
