package ua.com.foxminded.universityapp.service;

import ua.com.foxminded.universityapp.model.entity.Group;
import ua.com.foxminded.universityapp.model.entity.User;

public interface GroupService extends GenericEntityService<Group> {
    Group getByName(String name);
}
