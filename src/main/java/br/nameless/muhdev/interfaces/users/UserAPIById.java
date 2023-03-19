package br.nameless.muhdev.interfaces.users;

import br.nameless.muhdev.backend.BackendNMC;
import br.nameless.muhdev.interfaces.groupmanager.GroupManager;
import br.nameless.muhdev.interfaces.groupmanager.GroupMN;
import br.nameless.muhdev.utils.BCrypt;
import lombok.SneakyThrows;

import javax.sql.rowset.CachedRowSet;

public class UserAPIById implements User {

    private final int id;

    public UserAPIById(int id) {
        this.id = id;
    }

    @Override
    @SneakyThrows
    public String getName() {
        return BackendNMC.getBackend().query("SELECT * FROM nl2_users WHERE id=?", id).getString("nickname");
    }

    @Override
    public boolean userExists() {
        CachedRowSet query = BackendNMC.getBackend().query("SELECT * FROM nl2_users WHERE id=?", id);
        return query != null;
    }

    @Override
    public UserAPIById changePass(String newpass) {
        String gensalt = BCrypt.gensalt();
        String hashed = BCrypt.hashpw(newpass, gensalt);
        BackendNMC.getBackend().execute("UPDATE nl2_users SET password=? WHERE id=?", hashed, id);
        return this;
    }

    @Override
    @SneakyThrows
    public boolean verifyPass(String pass) {
        CachedRowSet query = BackendNMC.getBackend().query("SELECT * FROM nl2_users WHERE id=?", id);
        String password = query.getString("password");
        return BCrypt.checkpw(pass, password);
    }

    public void execute() {}

    @Override
    public int getId() {
        return id;
    }

    @Override
    public GroupManager getGroupMN() {
        return new GroupMN(this);
    }


}
