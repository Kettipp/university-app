package ua.com.foxminded.universityapp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private List<Class> classes = new ArrayList<>();
    @ManyToMany(mappedBy = "courses")
    @Builder.Default
    private List<Group> groups = new ArrayList<>();
    @ManyToMany(mappedBy = "courses")
    @Builder.Default
    private List<Teacher> teachers = new ArrayList<>();

    @Override
    public String toString() {
        return "{name=" + name + "}";
    }
}
