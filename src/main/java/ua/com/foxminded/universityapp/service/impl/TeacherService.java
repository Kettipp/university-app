package ua.com.foxminded.universityapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.universityapp.model.entity.Teacher;
import ua.com.foxminded.universityapp.model.entity.User;
import ua.com.foxminded.universityapp.model.repository.UserRepository;
import ua.com.foxminded.universityapp.service.UserService;

import java.util.List;

@Service
public class TeacherService implements UserService<Teacher> {
    private final UserRepository repository;

    @Autowired
    public TeacherService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addAll(List<Teacher> entities) {

    }

    @Override
    public void add(Teacher teacher) {
        repository.save(teacher);
    }

    @Override
    public List<Teacher> getAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public List<Teacher> getTeachers() {
        return null;
    }

    @Override
    public User getByTeacherName(String firstName, String lastName) {
        return repository.findByTeacherName(firstName, lastName);
    }
}
