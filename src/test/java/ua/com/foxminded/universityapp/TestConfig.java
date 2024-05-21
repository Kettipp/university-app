package ua.com.foxminded.universityapp;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.model.entity.Course;
import ua.com.foxminded.universityapp.model.entity.Group;
import ua.com.foxminded.universityapp.model.entity.Teacher;

@SpringBootConfiguration
@EnableJpaRepositories(basePackages = {"ua.com.foxminded.universityapp.repository", "ua.com.foxminded.universityapp.service"})
@ComponentScan(basePackages = {"ua.com.foxminded.universityapp.repository", "ua.com.foxminded.universityapp.service"})
@EntityScan(basePackageClasses = {Class.class, Course.class, Group.class, Teacher.class})
public class TestConfig {
}
