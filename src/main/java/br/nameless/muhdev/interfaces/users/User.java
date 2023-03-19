package br.nameless.muhdev.interfaces.users;

import br.nameless.muhdev.interfaces.groupmanager.GroupManager;

public interface User {

    String getName();

    boolean userExists();

    User changePass(String pass);

    boolean verifyPass(String pass);

    void execute();

    int getId();

    GroupManager getGroupMN();

}
