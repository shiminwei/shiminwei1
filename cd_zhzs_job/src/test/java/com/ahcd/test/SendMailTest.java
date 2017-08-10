package com.ahcd.test;

import javax.mail.MessagingException;

import com.ahcd.common.SimpleMailSender;
import com.ahcd.pojo.MailSenderInfo;

public class SendMailTest {
	public static void main(String[] args) {
		  // 设置邮件服务器信息
		  MailSenderInfo mailInfo = new MailSenderInfo();
		  mailInfo.setValidate(true);
		  // 收件人邮箱
		  mailInfo.setToAddress("45850277@qq.com");
		  // 邮件标题
		  mailInfo.setSubject("测试Java程序发送邮件");
		  // 邮件内容
		  StringBuffer buffer = new StringBuffer();
		  buffer.append("JavaMail 1.4.5 jar包下载地址：http://www.oracle.com/technetwork/java/index-138643.html\n");
		  buffer.append("JAF 1.1.1 jar包下载地址：http://www.oracle.com/technetwork/java/javase/downloads/index-135046.html");
		  mailInfo.setContent(buffer.toString());
		  
		  // 发送邮件
		 // SimpleMailSender sms = new SimpleMailSender();
		  // 发送文体格式
		  try {
			//sms.sendTextMail(mailInfo);
			SimpleMailSender.sendMail(new String[]{"45850277@qq.com"}, "测试Java程序发送邮件", "JavaMail 1.4.5 jar包下载地址：http://www.oracle.com/technetwork/java/index-138643.html\n");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		  System.out.println("邮件发送完毕");
		 }
}
