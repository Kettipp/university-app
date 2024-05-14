package ua.com.foxminded.universityapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.universityapp.model.entity.Class;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    List<Class> findClassByGroupName(String groupName);

    @Query("SELECT c FROM Class c WHERE c.teacher.firstName = :teacherFirstName and c.teacher.lastName = :teacherLastName")
    List<Class> findClassByTeacherName(@Param("teacherFirstName") String teacherFirstName, @Param("teacherLastName")String teacherLastName);
}
