package com.ahcd.common;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.ahcd.pojo.MailSenderInfo;

public class SimpleMailSender {
	/**
	 * 发送邮件给一个邮箱
	 * @param recieveEmail
	 * @param subjectTitlt
	 * @param content
	 * @return
	 * @throws MessagingException
	 */
	public static boolean sendMail(String recieveEmail,String subjectTitlt,String content) throws MessagingException {
		return sendMail(new String[]{recieveEmail}, subjectTitlt, content);
	}
	/**
	 *  发送邮件给多个邮箱
	 * @param recieveEmails
	 * @param subjectTitlt
	 * @param content
	 * @return
	 * @throws MessagingException
	 */
	public static boolean sendMail(String[] recieveEmails,String subjectTitlt,String content) throws MessagingException {
		if(recieveEmails==null || recieveEmails.length ==0) return false;
		MailSenderInfo mailInfo = new MailSenderInfo();
		// 判断是否需要身份认证
		EmailAccountAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new EmailAccountAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(sendMailSession);
		// 创建邮件发送者地址
		Address from = new InternetAddress(mailInfo.getFromAddress());
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address[] addresses=new Address[recieveEmails.length];
		for(int i=0;i<recieveEmails.length;i++){
			addresses[i]=new InternetAddress(recieveEmails[i]);
		}
		// Message.RecipientType.TO属性表示接收者的类型为TO
		mailMessage.setRecipients(Message.RecipientType.TO, addresses);
		// 设置邮件消息的主题
		mailMessage.setSubject(subjectTitlt);
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		Multipart mainPart = new MimeMultipart();
		// 创建一个包含HTML内容的MimeBodyPart
		BodyPart html = new MimeBodyPart();
		// 设置HTML内容
		html.setContent(content, "text/html; charset=utf-8");
		mainPart.addBodyPart(html);
		// 将MiniMultipart对象设置为邮件内容
		mailMessage.setContent(mainPart);
		// 发送邮件
		Transport.send(mailMessage);
		return true;
	}
	
	
}
