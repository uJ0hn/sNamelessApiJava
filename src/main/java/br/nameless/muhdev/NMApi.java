package br.nameless.muhdev;

import br.nameless.muhdev.backend.BackendNMC;
import br.nameless.muhdev.integrations.forms.Forms;
import br.nameless.muhdev.interfaces.groups.Group;
import br.nameless.muhdev.interfaces.groups.GroupAPIById;
import br.nameless.muhdev.interfaces.groups.GroupAPIByName;
import br.nameless.muhdev.interfaces.users.User;
import br.nameless.muhdev.interfaces.users.UserAPIById;
import br.nameless.muhdev.interfaces.users.UserAPIByName;
import br.nameless.muhdev.utils.Utils;

public class NMApi {

	
	private final String url;
	
	
	private NMApi(String url, String myhost, String myport, String mydb, String myuser, String mypass) {
		this.url = url;
		BackendNMC.backend = new BackendNMC(myhost, myport,mydb, myuser, mypass);
	}
	
	
	public static User getUserByName(String name) {
		return new UserAPIByName(name);
	}

	public static User getUserByID(int id) {
		return new UserAPIById(id);
	}


	public static Group getGroupByName(String name) {
		return new GroupAPIByName(name);
	}

	public static Group getGroupById(int id) {
		return new GroupAPIById(id);
	}

	public String getUrl() {
		return this.url;
	}
	

	public Forms getFormsModule(int formid) {
		return new Forms(formid);
	}


	public static NMApi getNameless(String url, String myhost, String myport, String mydb, String myuser, String mypass) {
		return new NMApi(url, myhost, myport,mydb, myuser, mypass);
	}
	
}
