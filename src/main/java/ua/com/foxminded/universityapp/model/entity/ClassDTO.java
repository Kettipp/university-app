package ua.com.foxminded.universityapp.model.entity;

import lombok.Data;

import java.time.DayOfWeek;

@Data
public class ClassDTO {
    private long id;
    private DayOfWeek day;
    private ClassTime time;
    private long groupId;
    private long courseId;
    private long teacherId;
}
