package ua.com.foxminded.universityapp.model.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
//@Data
@SuperBuilder
@NoArgsConstructor
//@DiscriminatorValue("Admin")
public class Admin extends User{
}
