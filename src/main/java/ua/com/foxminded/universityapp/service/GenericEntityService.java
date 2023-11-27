package ua.com.foxminded.universityapp.service;

import java.util.List;

public interface GenericEntityService<T> {
    void addAll(List<T> entities);
    void add(T entity);
    List<T> getAll();
    T getById(long id);
    void deleteById(long id);
}
