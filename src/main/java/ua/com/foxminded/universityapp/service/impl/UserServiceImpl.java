package ua.com.foxminded.universityapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.universityapp.model.UserRepository;
import ua.com.foxminded.universityapp.model.entity.User;
import ua.com.foxminded.universityapp.service.UserService;

import java.util.List;
@Service
public class UserServiceImpl implements UserService<User> {
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(User user) {
        repository.save(user);
    }

    @Override
    public void addAll(List<User> entities) {
        repository.saveAll(entities);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User getById(long id) {
        return repository.getReferenceById(id);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
