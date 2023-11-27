package ua.com.foxminded.universityapp.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {
    @ManyToMany
    @JoinTable(name = "teacher_courses",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @Builder.Default
    private Set<Course> courses = new HashSet<>();
    @OneToMany(mappedBy = "teacher")
    private List<Class> clas = new ArrayList<>();
}
