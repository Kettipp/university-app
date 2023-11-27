package ua.com.foxminded.universityapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.universityapp.model.entity.Class;

public interface ClassRepository extends JpaRepository<Class, Long> {
}
