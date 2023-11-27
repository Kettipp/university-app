package ua.com.foxminded.universityapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<Student> students = new ArrayList<>();
    @OneToMany(mappedBy = "group")
    @Builder.Default
    private List<Class> classes = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "group_courses",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @Builder.Default
    private Set<Course> courses = new HashSet<>();
}

