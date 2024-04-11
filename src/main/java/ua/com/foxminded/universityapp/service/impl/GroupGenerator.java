package ua.com.foxminded.universityapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.com.foxminded.universityapp.model.entity.Course;
import ua.com.foxminded.universityapp.model.entity.Group;
import ua.com.foxminded.universityapp.service.Generate;

import java.time.DayOfWeek;
import java.util.*;

@Component
public class GroupGenerator implements Generate<Group> {
    private final int groupsLimit;
    private final int groupsCount;
    private static final  int ALPHABET_SIZE = 26;
    private static final  String FORMAT = "%d%d-%s%s";


    public GroupGenerator(@Value("${university.groupsLimit}") int groupsLimit,
                          @Value("${university.groupsCount}")int groupsCount) {
        this.groupsLimit = groupsLimit;
        this.groupsCount = groupsCount;
    }

    @Override
    public List<Group> generate() {
        Random random = new Random();
        ArrayList<Group> groupsNames = new ArrayList<>();
        for (int i = 0; i < groupsCount; i++) {
            String groupNameFormat = String.format(FORMAT,
                    random.nextInt(groupsLimit),
                    random.nextInt(groupsLimit),
                    (char)('A' + random.nextInt(ALPHABET_SIZE)),
                    (char)('A' + random.nextInt(ALPHABET_SIZE))
            );
            groupsNames.add(Group.builder().name(groupNameFormat).build());
        }
        return groupsNames;
    }
}
