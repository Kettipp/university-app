package ua.com.foxminded.universityapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.universityapp.model.entity.Teacher;
import ua.com.foxminded.universityapp.model.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.role = 'Teacher'")
    List<Teacher> findTeachers();

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.firstName = :firstname AND u.lastName = :lastname AND u.role = 'Teacher'")
    User findByTeacherName(@Param("firstname") String firstName, @Param("lastname") String lastName);
}
