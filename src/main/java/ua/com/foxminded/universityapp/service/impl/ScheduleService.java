package ua.com.foxminded.universityapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;

import java.time.*;
import java.util.*;
import java.util.function.Function;

@Component
@Slf4j
public class ScheduleService {
    private static final int WORK_DAYS_COUNT = 5;

    public List<Schedule> generate(List<Class> classes) {
        List<Schedule> schedule = new ArrayList<>();
        List<LocalDate> dates = weekDates();
        List<DayOfWeek> days = workDays();
        for (int i = 0; i < WORK_DAYS_COUNT; i++) {
            DayOfWeek day = days.get(i);
            Map<ClassTime, List<Class>> classesPerTime = new LinkedHashMap<>();
            for (ClassTime time : ClassTime.values()) {
                classesPerTime.put(time,
                        classes.stream()
                        .filter(x -> x.getTime().equals(time) && x.getDay().equals(day))
                        .sorted(Comparator.comparing(cl -> cl.getGroup().getId())).toList());
            }
            schedule.add(new Schedule(dates.get(i), day, classesPerTime));
        }
        schedule.sort(Comparator.comparing(Schedule::getDate));
        return schedule;
    }

    public Map<LocalDate, List<Class>> singleSchedule(List<Class> classes, List<ClassTime> classTime) {
        Map<LocalDate, List<Class>> singleSchedule = new LinkedHashMap<>();
        for (LocalDate date : weekDates()) {
            singleSchedule.put(date, filterClassesByDay(classes, date));
        }
        HashMap<LocalDate, List<Class>> collect = new LinkedHashMap<>();
        singleSchedule.entrySet().stream().peek(e -> {
            List<Class> classesForDay = new ArrayList<>(e.getValue());
            List<ClassTime> classTimesForDay = classesForDay.stream().map(Class::getTime).toList();
            classTime.stream().filter(x -> !classTimesForDay.contains(x)).forEach(time -> classesForDay.add(Class.builder()
                    .course(Course.builder().name("-").build())
                    .group(Group.builder().name("-").build())
                    .time(time)
                    .build()
            ));
            classesForDay.sort(Comparator.comparing(Class::getTime));
            e.setValue(classesForDay);
        }).forEach(e -> collect.put(e.getKey(), e.getValue()));
        return collect;
    }

    private List<LocalDate> weekDates() {
        List<LocalDate> days = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(DayOfWeek.MONDAY);
        for (int i = 0; i < WORK_DAYS_COUNT; i++) {
            days.add(startOfWeek.plusDays(i));
        }
        return days;
    }

    private List<DayOfWeek> workDays() {
        return Arrays.stream(DayOfWeek.values())
                .filter(x -> !(x.equals(DayOfWeek.SATURDAY) || x.equals(DayOfWeek.SUNDAY))).toList();
    }

    private List<Class> filterClassesByDay(List<Class> classes, LocalDate date) {
        return classes.stream()
                .filter(clas -> clas.getDay().equals(date.getDayOfWeek()))
                .sorted(Comparator.comparing(Class::getTime)).toList();
    }
}
