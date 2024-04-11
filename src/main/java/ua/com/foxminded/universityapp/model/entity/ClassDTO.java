package ua.com.foxminded.universityapp.model.entity;

import lombok.Data;

import java.time.DayOfWeek;

@Data
public class ClassDTO {
    private DayOfWeek day;
    private ClassTime time;
    private String groupName;
    private String courseName;
    private String teacherFirstname;
    private String teacherLastname;
}
