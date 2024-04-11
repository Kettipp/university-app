package ua.com.foxminded.universityapp.model.entity;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String role;
}
