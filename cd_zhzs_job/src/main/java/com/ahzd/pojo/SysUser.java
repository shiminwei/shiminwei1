package com.ahzd.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "zhzs_job_user")
public class SysUser {
	private String _id;
	private String userName;
	private String email;
	private String phoneNumber;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
