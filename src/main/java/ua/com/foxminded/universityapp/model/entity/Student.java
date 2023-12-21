package ua.com.foxminded.universityapp.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("Student")
public class Student extends User {
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
