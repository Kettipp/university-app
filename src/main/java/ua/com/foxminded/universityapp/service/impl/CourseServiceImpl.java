package ua.com.foxminded.universityapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.universityapp.model.CourseRepository;
import ua.com.foxminded.universityapp.model.entity.Course;
import ua.com.foxminded.universityapp.service.CourseService;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;

    @Autowired
    public CourseServiceImpl(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addAll(List<Course> courses) {
        repository.saveAll(courses);
    }

    @Override
    public void add(Course course) {
        repository.save(course);
    }

    @Override
    public List<Course> getAll() {
        return repository.findAll();
    }

    @Override
    public Course getById(long id) {
        return repository.getReferenceById(id);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
