package br.nameless.muhdev.interfaces.groupmanager;

import br.nameless.muhdev.NMApi;
import br.nameless.muhdev.backend.BackendNMC;
import br.nameless.muhdev.interfaces.groups.Group;
import br.nameless.muhdev.interfaces.users.User;
import lombok.SneakyThrows;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GroupMN implements GroupManager {
    private final User user;

    public GroupMN(User user){
        this.user = user;
    }

    @Override
    @SneakyThrows
    public GroupManager setGroup(Group group) {
        CachedRowSet query = BackendNMC.getBackend().query("SELECT * FROM nl2_users_groups WHERE user_id=?", user.getId());
        if(query != null) {
            BackendNMC.getBackend().execute("DELETE FROM nl2_users_groups WHERE user_id=?", user.getId());
        }

        CachedRowSet q = BackendNMC.getBackend().query("SELECT * FROM nl2_users_groups ORDER BY received DESC");

        new Timer().schedule(new TimerTask() {
            @Override
            @SneakyThrows
            public void run() {
                BackendNMC.getBackend().execute("INSERT INTO nl2_users_groups VALUES (?, ?, ?, ?, ?)", (q.getInt("id") + 1), user.getId(), group.getID(), (System.currentTimeMillis() / 1000), 0);
            }
        }, 2L);

        return this;
    }

    @Override
    @SneakyThrows
    public boolean hasGroup(Group group) {
        CachedRowSet query = BackendNMC.getBackend().query("SELECT * FROM nl2_users_groups WHERE user_id=?", user.getId());
        List<String> strings = new ArrayList<>();
        for(int i = 0 ; i < query.size() ; i++) {
            strings.add(NMApi.getGroupById(query.getInt("group_id")).getName().toUpperCase());
            query.next();
        }

        return strings.contains(group.getName().toUpperCase());
    }

    @Override
    @SneakyThrows
    public List<Group> getGroups() {
        CachedRowSet query = BackendNMC.getBackend().query("SELECT * FROM nl2_users_groups WHERE user_id=?", user.getId());
        List<Group> strings = new ArrayList<>();
        for(int i = 0 ; i < query.size() ; i++) {
            strings.add(NMApi.getGroupById(query.getInt("group_id")));
            query.next();
        }

        return strings;
    }

    public void execute() {}


}
