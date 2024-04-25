package ua.com.foxminded.universityapp.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @SequenceGenerator(name = "c_seq", sequenceName = "courses_sequence", allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_seq")
    private long id;
    private String name;
    @OneToMany(mappedBy = "course")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Class> classes = new HashSet<>();
    @ManyToMany(mappedBy = "courses")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Group> groups = new HashSet<>();
    @ManyToMany(mappedBy = "courses")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Teacher> teachers = new HashSet<>();

    @Override
    public String toString() {
        return "{name=" + name + "}";
    }
}
