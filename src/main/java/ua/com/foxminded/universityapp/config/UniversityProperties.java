package ua.com.foxminded.universityapp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
@ConfigurationProperties(prefix = "university")
@Setter
public class UniversityProperties {
    private List<String> courseNames;
    private List<String> firstNames;
    private List<String> lastNames;
}
