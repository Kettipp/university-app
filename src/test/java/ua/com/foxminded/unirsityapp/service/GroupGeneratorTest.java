package ua.com.foxminded.unirsityapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.foxminded.universityapp.model.entity.Group;
import ua.com.foxminded.universityapp.service.Generate;
import ua.com.foxminded.universityapp.service.impl.GroupGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {GroupGenerator.class})
public class GroupGeneratorTest {
    @Autowired
    private Generate<Group> groupGenerator;

    @Test
    public void generate_shouldGenerateGroupNames() {
        int expected = 10;

        List<Group> groups = groupGenerator.generate();
        int actual = groups.size();

        assertEquals(expected, actual);

    }
}
