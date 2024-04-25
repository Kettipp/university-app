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
@Table(name = "groups")
public class Group {
    @Id
    @SequenceGenerator(name = "g_seq", sequenceName = "groups_sequence", allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "g_seq")
    private long id;
    private String name;
    @OneToMany(mappedBy = "group")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Student> students = new HashSet<>();
    @OneToMany(mappedBy = "group")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Class> classes = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "group_courses",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Course> courses = new HashSet<>();

    @Override
    public String toString() {
        return "{name=" + name + "}";
    }
}

