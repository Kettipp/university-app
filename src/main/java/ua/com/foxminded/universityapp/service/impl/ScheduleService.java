package ua.com.foxminded.universityapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ua.com.foxminded.universityapp.model.entity.*;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.service.GroupService;

import java.time.*;
import java.util.*;

@Component
@Slf4j
public class ScheduleService {
    private static final int WORK_DAYS_COUNT = 5;

    private static final int GROUPS_COUNT = 5;


    public List<Schedule> generate(List<Class> classes) {
        return scheduleTemplates()
                .stream()
                .map(schedule -> schedule.toBuilder()
                        .classes(replaceExists(schedule, classes))
                        .build()
                )
                .toList();
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
                    .teacher(Teacher.builder().firstName("-").lastName("-").build())
                    .time(time)
                    .build()
            ));
            classesForDay.sort(Comparator.comparing(Class::getTime));
            e.setValue(classesForDay);
        }).forEach(e -> collect.put(e.getKey(), e.getValue()));
        return collect;
    }

    private Map<ClassTime, List<Class>> replaceExists(Schedule schedule, List<Class> classes) {
        List<Map.Entry<ClassTime, List<Class>>> list = schedule.getClasses()
                .entrySet()
                .stream()
                .map(ent -> Map.entry(ent.getKey(), ent.getValue()
                                .stream()
                                .map(clas -> getIfExists(schedule, ent.getKey(), classes).orElse(clas))
                                .sorted(Comparator.comparing(cl -> cl.getGroup().getId()))
                                .toList()
                        )
                )
                .sorted(Map.Entry.comparingByKey())
                .toList();
        LinkedHashMap<ClassTime, List<Class>> map = new LinkedHashMap<>();
        list.forEach(e -> map.put(e.getKey(), e.getValue()));
        return map;
    }

    private Optional<Class> getIfExists(Schedule schedule, ClassTime time, List<Class> classes) {
        Optional<Class> first = classes.stream()
                .filter(cl -> Objects.equals(cl.getDay(), schedule.getDay()) && Objects.equals(cl.getTime(), time))
                .findFirst();
        first.ifPresent(classes::remove);
        return first;
    }

    private @NotNull List<Schedule> scheduleTemplates() {
        List<Schedule> schedule = new ArrayList<>();
        List<LocalDate> dates = weekDates();
        List<DayOfWeek> days = workDays();
        for (int i = 0; i < WORK_DAYS_COUNT; i++) {
            DayOfWeek day = days.get(i);
            Map<ClassTime, List<Class>> classesPerTime = new LinkedHashMap<>();
            for (ClassTime time : ClassTime.values()) {
                List<Class> cl = new ArrayList<>();
                for (int k = 0; k < GROUPS_COUNT; k++) {
                    cl.add(Class.builder()
                            .day(day)
                            .time(time)
                            .group(Group.builder().name("-").build())
                            .teacher(Teacher.builder().firstName("-").lastName("-").build())
                            .course(Course.builder().name("-").build())
                            .build());
                }
                classesPerTime.put(time, cl);
            }
            schedule.add(new Schedule(dates.get(i), day, classesPerTime));
        }
        return schedule;
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
