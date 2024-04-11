package ua.com.foxminded.universityapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ua.com.foxminded.universityapp.model.entity.Teacher;

@Component
public class RunService implements ApplicationRunner {
    @Value("${isDebug}") public boolean isDebug;
    private final Initialization initialization;

    @Autowired
    public RunService(Initialization initialization) {
        this.initialization = initialization;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(isDebug) {
            initialization.init();
        }
    }
}
