package br.nameless.muhdev.integrations.forms;

import br.nameless.muhdev.backend.BackendNMC;
import br.nameless.muhdev.integrations.Form;
import br.nameless.muhdev.interfaces.users.User;
import lombok.SneakyThrows;

import javax.sql.rowset.CachedRowSet;
import java.util.HashMap;
import java.util.Map;

public class FormView implements Form {

    private final int formid;
    private final User user;

    public FormView(int formid, User user) {
        this.formid = formid;
        this.user = user;
    }

    private CachedRowSet query() {
        return BackendNMC.getBackend().query("SELECT * FROM nl2_forms_replies WHERE form_id=? AND user_id=?", formid, user.getId());
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public boolean formExists() {
        return query() != null;
    }

    @Override
    public Forms getForm() {
        return new Forms(formid);
    }

    @Override
    @SneakyThrows
    public Map<String, String> getFormReplies() {
        Map<String, String> formsreplies = new HashMap<>();
        Forms forms = getForm();
        for(Map.Entry<Integer, String> map : forms.getFormFields().entrySet()) {
            CachedRowSet query = BackendNMC.getBackend().query("SELECT * FROM nl2_forms_replies_fields WHERE submission_id=? AND field_id=?", query().getInt("id"), map.getKey());
            formsreplies.put(map.getValue(), query.getString("value"));
        }

        return formsreplies;
    }


}
