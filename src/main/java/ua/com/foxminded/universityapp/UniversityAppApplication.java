package ua.com.foxminded.universityapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.com.foxminded.universityapp.model.entity.Teacher;
import ua.com.foxminded.universityapp.service.UserService;

import java.util.ArrayList;

@SpringBootApplication
public class UniversityAppApplication {
    public static void main(String[] args) {SpringApplication.run(UniversityAppApplication.class, args);}
}
