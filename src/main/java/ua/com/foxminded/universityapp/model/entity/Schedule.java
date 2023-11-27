package ua.com.foxminded.universityapp.model.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class Schedule {
    private LocalDate date;
    private List<Class> classes;
}
