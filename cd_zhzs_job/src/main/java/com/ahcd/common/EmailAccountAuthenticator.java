package com.ahcd.common;


//邮箱用户名和密码认证器
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailAccountAuthenticator extends Authenticator {
	 String userName = null;
	 String password = null;
	 public EmailAccountAuthenticator() {
	 }
	 public EmailAccountAuthenticator(String username, String password) {
	  this.userName = username;
	  this.password = password;
	 }
	 protected PasswordAuthentication getPasswordAuthentication() {
	  return new PasswordAuthentication(userName, password);
	 }
}