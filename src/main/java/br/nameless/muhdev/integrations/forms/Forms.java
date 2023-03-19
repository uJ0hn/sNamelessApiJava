package br.nameless.muhdev.integrations.forms;

import br.nameless.muhdev.backend.BackendNMC;
import br.nameless.muhdev.interfaces.users.User;
import lombok.SneakyThrows;

import javax.sql.rowset.CachedRowSet;
import java.util.HashMap;
import java.util.Map;

public class Forms {


    private final int formid;

    public Forms(int formid) {
        this.formid = formid;
    }

    public static boolean isOn() {
        return BackendNMC.getBackend().query("SELECT * FROM nl2_forms") != null;
    }

    public boolean formExists() {
        return BackendNMC.getBackend().query("SELECT * FROM nl2_forms WHERE id=?", formid) != null;
    }

    @SneakyThrows
    public Map<Integer, String> getFormFields() {
        CachedRowSet query = BackendNMC.getBackend().query("SELECT * FROM nl2_forms_fields WHERE form_id=?", formid);
        Map<Integer, String> strings = new HashMap<>();
        for(int i = 0 ; i < query.size() ; i++) {
            strings.put(query.getInt("id"), query.getString("name"));
            query.next();
        }

        return strings;
    }


    public FormView getForm(User user) {
        return new FormView(formid, user);
    }

}
