package com.noah.server.permission;

public class User {
	
	private String userName;
	private String role;
	private String pwd;
	
	public User(){
		
	}
	
	public User(String userName, String role, String pwd){
		this.userName = userName;
		this.role = role;
		this.pwd = pwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	

}
