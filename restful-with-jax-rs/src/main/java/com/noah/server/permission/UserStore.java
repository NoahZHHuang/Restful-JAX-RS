package com.noah.server.permission;

import java.util.HashMap;
import java.util.Map;

public class UserStore {
	
	public static final Map<String, User> users = new HashMap<>();
	
	static {
		users.put("Noah", new User("Noah",Role.ADMIN_USER,"NoahPwd"));
		users.put("Allie", new User("Allie",Role.NORMAL_USER,"AlliePwd"));
	}

	public static User getUser(String userName){
		return users.get(userName);
	}
	
}
