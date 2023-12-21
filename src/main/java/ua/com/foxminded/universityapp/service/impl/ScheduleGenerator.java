package ua.com.foxminded.universityapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.model.entity.Schedule;
import ua.com.foxminded.universityapp.service.ClassService;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleGenerator {
    private final ClassService classService;

    @Autowired
    public ScheduleGenerator(ClassService classService) {
        this.classService = classService;
    }

    public List<Schedule> generate() {
        List<Schedule> schedule = new ArrayList<>();
        List<Class> classes = classService.getAll();
        for(DayOfWeek day : DayOfWeek.values()) {
            List<Class> classList = classes.stream().filter(aClass -> aClass.getDay().equals(day)).toList();
            daysOfWeekDates(day).forEach(d -> schedule.add(new Schedule(d, classList)));
        }
        return schedule;
    }

    private List<LocalDate> daysOfWeekDates(DayOfWeek day) {
        YearMonth yearMonth = YearMonth.now();
        int lengthOfMonth = yearMonth.lengthOfMonth();
        List<LocalDate> days = new ArrayList<>();
        for(int i = 1; i < lengthOfMonth; i++) {
            LocalDate localDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth().getValue(), i);
            if(localDate.getDayOfWeek().equals(day)) {
                days.add(localDate);
            }
        }
        return days;
    }
}
