package ua.com.foxminded.universityapp.model.entity;

import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;

@Data
@Builder
public class ClassDTO {
    private long id;
    private DayOfWeek day;
    private ClassTime time;
    private long groupId;
    private long courseId;
    private long teacherId;
}
