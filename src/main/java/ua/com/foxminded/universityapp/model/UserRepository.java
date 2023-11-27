package ua.com.foxminded.universityapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.universityapp.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
