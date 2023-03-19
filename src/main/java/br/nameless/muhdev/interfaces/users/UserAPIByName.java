package br.nameless.muhdev.interfaces.users;

import br.nameless.muhdev.backend.BackendNMC;
import br.nameless.muhdev.interfaces.groupmanager.GroupManager;
import br.nameless.muhdev.interfaces.groupmanager.GroupMN;
import br.nameless.muhdev.utils.BCrypt;
import lombok.SneakyThrows;

import javax.sql.rowset.CachedRowSet;

public class UserAPIByName implements User {

    private final String id;

    public UserAPIByName(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return id;
    }

    @Override
    public boolean userExists() {
        CachedRowSet query = BackendNMC.getBackend().query("SELECT * FROM nl2_users WHERE nickname=?", id);
        return query != null;
    }

    @Override
    public UserAPIByName changePass(String newpass) {
        String gensalt = BCrypt.gensalt();
        String hashed = BCrypt.hashpw(newpass, gensalt);
        BackendNMC.getBackend().execute("UPDATE nl2_users SET password=? WHERE nickname=?", hashed, id);
        return this;
    }

    @Override
    @SneakyThrows
    public boolean verifyPass(String pass) {
        CachedRowSet query = BackendNMC.getBackend().query("SELECT * FROM nl2_users WHERE nickname=?", id);
        String password = query.getString("password");
        return BCrypt.checkpw(pass, password);
    }

    public void execute() {}

    @Override
    @SneakyThrows
    public int getId() {
        return BackendNMC.getBackend().query("SELECT * FROM nl2_users WHERE nickname=?", id).getInt("id");
    }



    @Override
    public GroupManager getGroupMN() {
        return new GroupMN(this);
    }

}
