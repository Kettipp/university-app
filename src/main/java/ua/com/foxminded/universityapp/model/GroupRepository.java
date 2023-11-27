package ua.com.foxminded.universityapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.foxminded.universityapp.model.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
