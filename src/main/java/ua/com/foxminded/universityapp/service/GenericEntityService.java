package ua.com.foxminded.universityapp.service;

import ua.com.foxminded.universityapp.model.entity.User;

import java.util.List;

public interface GenericEntityService<T> {
    void addAll(List<T> entities);
    void add(T entity);
    List<T> getAll();
    void deleteById(long id);
}
