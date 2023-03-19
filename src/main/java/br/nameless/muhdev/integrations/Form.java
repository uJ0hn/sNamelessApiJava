package br.nameless.muhdev.integrations;

import br.nameless.muhdev.integrations.forms.Forms;
import br.nameless.muhdev.interfaces.users.User;

import java.util.Map;

public interface Form {

    User getUser() ;

    boolean formExists();

    Forms getForm();


    public Map<String, String> getFormReplies() ;


}
