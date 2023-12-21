package ua.com.foxminded.universityapp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "u_seq", sequenceName = "users_sequence", allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "u_seq")
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String login;
    private String password;
//custom equals may be required
}
