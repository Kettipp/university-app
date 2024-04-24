package ua.com.foxminded.universityapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.universityapp.model.repository.GroupRepository;
import ua.com.foxminded.universityapp.model.entity.Group;
import ua.com.foxminded.universityapp.service.GroupService;

import java.util.List;
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository repository;

    @Autowired
    public GroupServiceImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addAll(List<Group> groups) {
        repository.saveAll(groups);
    }

    @Override
    public void add(Group group) {
        repository.save(group);
    }

    @Override
    public List<Group> getAll() {
        return repository.findAll();
    }

    @Override
    public Group getById(long id){
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    @Override
    public Group getByName(String name) {
        return repository.findByName(name);
    }
}
