package br.nameless.muhdev.interfaces.groups;

import br.nameless.muhdev.backend.BackendNMC;
import lombok.SneakyThrows;

import javax.sql.rowset.CachedRowSet;

public class GroupAPIByName implements Group {

    private final String name;

    public GroupAPIByName(String name) {
        this.name = name.toLowerCase();
    }


    private CachedRowSet query() {
        return BackendNMC.getBackend().query("SELECT * FROM nl2_groups WHERE LOWER(name) LIKE LOWER(?)", name);
    }

    @Override
    public boolean groupExists() {
        return query() != null;
    }

    @Override
    public String getName() {
        return name;
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
        BackendNMC.getBackend().execute("UPDATE nl2_groups SET name=? WHERE LOWER(name) LIKE LOWER(?)", n, name);
        return this;
    }

    @Override
    public Group setColor(String n) {
        BackendNMC.getBackend().execute("UPDATE nl2_groups SET group_username_color=? WHERE LOWER(name) LIKE LOWER(?)", n, name);
        return this;
    }

    @Override
    public Group setHtml(String n) {
        BackendNMC.getBackend().execute("UPDATE nl2_groups SET group_html=? WHERE LOWER(name) LIKE LOWER(?)", n, name);
        return this;
    }


    public void execute() {}
}
