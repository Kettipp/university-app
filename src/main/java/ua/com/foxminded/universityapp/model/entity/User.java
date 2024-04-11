package ua.com.foxminded.universityapp.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@Table(name = "users")
public class User {
    @Builder.Default
    @Id
    @SequenceGenerator(name = "u_seq", sequenceName = "users_sequence", allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "u_seq")
    private long id = 0L;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String username;
    private String password;
    @Column(name = "role", insertable=false, updatable = false)
    private String role;
}
