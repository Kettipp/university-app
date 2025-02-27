package ua.com.foxminded.universityapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Schedule {
    private LocalDate date;
    private DayOfWeek day;
    private Map<ClassTime, List<Class>> classes;
}
