package ua.com.foxminded.universityapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.universityapp.model.entity.Group;
import ua.com.foxminded.universityapp.model.entity.Teacher;
import ua.com.foxminded.universityapp.model.repository.UserRepository;

import java.util.List;

@Service
public class TeacherService  {
    private final UserRepository repository;

    @Autowired
    public TeacherService(UserRepository repository) {
        this.repository = repository;
    }

    public Teacher getById(long id){
        return (Teacher) repository.findById(id).orElseThrow();
    }
//
//    @Autowired
//    public TeacherService(UserRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public void addAll(List<Teacher> entities) {
//
//    }
//
//    @Override
//    public void add(Teacher teacher) {
//        repository.save(teacher);
//    }
//
//    @Override
//    public List<Teacher> getAll() {
//        return null;
//    }
//
//    @Override
//    public void deleteById(long id) {
//
//    }
//

    public List<Teacher> getTeachers() {
        return repository.findTeachers();
    }
//

    public Teacher getByTeacherName(String firstName, String lastName) {
        return repository.findByTeacherName(firstName, lastName);
    }
}
