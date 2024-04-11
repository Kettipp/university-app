package ua.com.foxminded.universityapp.service;

import ua.com.foxminded.universityapp.model.entity.Course;
import ua.com.foxminded.universityapp.model.entity.Group;

import java.time.DayOfWeek;
import java.util.List;

public interface Generate<Type> {
    List<Type> generate();
}
