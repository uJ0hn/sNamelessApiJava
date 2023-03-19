package br.nameless.muhdev.interfaces.groupmanager;

import br.nameless.muhdev.interfaces.groups.Group;

import java.util.List;

public interface GroupManager {

    GroupManager setGroup(Group group);

    boolean hasGroup(Group group);

    List<Group> getGroups();

    void execute();

}
