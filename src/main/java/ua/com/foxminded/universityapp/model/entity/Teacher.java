package ua.com.foxminded.universityapp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("Teacher")
public class Teacher extends User {
    @ManyToMany
    @JoinTable(name = "teachers_courses",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Course> courses = new HashSet<>();
    @Builder.Default
    @OneToMany(mappedBy = "teacher")
    @EqualsAndHashCode.Exclude
    private Set<Class> clas = new HashSet<>();

    @Override
    public String toString() {
        return "{name: " + super.getFirstName() + ", secondName: " + super.getLastName() + "}";
    }
}
