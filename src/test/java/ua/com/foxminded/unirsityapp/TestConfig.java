package ua.com.foxminded.unirsityapp;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ua.com.foxminded.universityapp.model.entity.Class;
import ua.com.foxminded.universityapp.model.entity.Course;
import ua.com.foxminded.universityapp.model.entity.Group;
import ua.com.foxminded.universityapp.model.entity.Teacher;
import ua.com.foxminded.universityapp.repository.ClassRepository;
import ua.com.foxminded.universityapp.service.ClassService;
import ua.com.foxminded.universityapp.service.impl.ClassServiceImpl;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = "ua.com.foxminded.universityapp.repository")
@EntityScan(basePackageClasses = {Class.class, Course.class, Group.class, Teacher.class})
public class TestConfig {
}
