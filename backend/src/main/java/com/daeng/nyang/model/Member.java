package com.daeng.nyang.model;

public class Member {
	
	private String user_name;
	private String user_id;
	private String user_email;
	private String user_password;
	private String user_token;
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUsertoken() {
		return user_token;
	}
	public void setUsertoken(String usertoken) {
		this.user_token = user_token;
	}
	
	@Override
	public String toString() {
		return "Member [user_name=" + user_name + ", user_id=" + user_id + ", user_email=" + user_email
				+ ", user_password=" + user_password + ", user_token=" + user_token + "]";
	}
}
