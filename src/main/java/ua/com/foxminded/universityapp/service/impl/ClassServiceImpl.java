package ua.com.foxminded.universityapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.universityapp.model.ClassRepository;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.service.ClassService;

import java.util.List;
@Service
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
        return repository.getReferenceById(id);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}