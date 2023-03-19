package br.nameless.muhdev.interfaces.groups;

import br.nameless.muhdev.backend.BackendNMC;
import lombok.SneakyThrows;

import javax.sql.rowset.CachedRowSet;

public class GroupAPIById implements Group {

    private final int id;

    public GroupAPIById(int id) {
        this.id = id;
    }


    private CachedRowSet query() {
        return BackendNMC.getBackend().query("SELECT * FROM nl2_groups WHERE id=?", id);
    }

    @Override
    public boolean groupExists() {
        return query() != null;
    }

    @Override
    @SneakyThrows
    public String getName() {
        return query().getString("name").toLowerCase();
    }

    @Override
    @SneakyThrows
    public String getColor() {
        return query().getString("group_username_color");
    }

    @Override
    @SneakyThrows
    public String getHtml() {
        return query().getString("group_html");
    }

    @Override
    @SneakyThrows
    public int getID() {
        return query().getInt("id");
    }

    @Override
    @SneakyThrows
    public boolean isStaff() {
        return query().getBoolean("staff");
    }

    @Override
    public Group setName(String n) {
        BackendNMC.getBackend().execute("UPDATE nl2_groups SET name=? WHERE id=?", n, id);
        return this;
    }

    @Override
    public Group setColor(String n) {
        BackendNMC.getBackend().execute("UPDATE nl2_groups SET group_username_color=? WHERE id=?", n, id);
        return this;
    }

    @Override
    public Group setHtml(String n) {
        BackendNMC.getBackend().execute("UPDATE nl2_groups SET group_html=? WHERE id=?", n, id);
        return this;
    }


    public void execute() {}
}
