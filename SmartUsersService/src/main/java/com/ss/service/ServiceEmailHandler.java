/**SmartSoftware User - Service */
/**
 * Description: ServiceEmailHandler
 * Name of Project: SmartSoftware
 * Created on: March 20, 2020
 * Modified on: March 20, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */

package com.ss.service;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.ss.constant.TemplateConstant;
import com.ss.model.EmailTemplate;
import com.ss.repository.RepositoryEmailTemplates;


@EnableAsync
@Service("serviceEmailHandler")
public class ServiceEmailHandler {

	private static final Log LOG = LogFactory.getLog(ServiceEmailHandler.class);

	@Value("${smtp.sender}")
	private String sender;

	@Value("${smtp.host}")
	private String host;

	@Value("${smtp.port}")
	private int port;

	@Value("${smtp.username}")
	private String username;

	@Value("${smtp.password}")
	private String password;

	@Autowired
	RepositoryEmailTemplates repositoryEmailTemplates;
	
	/**
	 * @return
	 */
	public Session getMailSession() {
		Properties props = /*javaMailSenderImpl.getJavaMailProperties();*/ new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.socketFactory.port",port); //"465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.starttls.enable", "true");

		return Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		 

	}

	/**
	 * @param email
	 * @param emailText
	 * @param subject
	 */
	public void sendEmail(String email, String emailText, String subject) throws Exception {
		    
			Session session = getMailSession();
			try 
			{
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(this.username, this.sender));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
				message.setSubject(subject);
				BodyPart body = new MimeBodyPart();
				body.setContent(emailText, "text/html; charset=ISO-8859-1");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(body);
				message.setContent(multipart, "text/html; charset=ISO-8859-1");
				Transport.send(message);
				 
			} 
			catch (Exception e) 
			{
				 LOG.info(Arrays.toString(e.getStackTrace()));
				 throw e;
			}
	}
	
	/**
	 * @param templateName
	 * @return
	 */
	public String[] getEmailTemplateTextByTemplateName(String templateName)
	{
		EmailTemplate template = repositoryEmailTemplates.findByTemplateName(templateName);
		if (template != null) 
		{
			if (!template.getTemplateText().isEmpty()) 
			{
				return new String[]{template.getTemplateText(),template.getTemplateSubject()};
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

		
	/**
	 * @param email
	 * @param newPassword
	 * @param username
	 * @return
	 */
//	@Async
	public boolean sendForgotPasswordEmail(String email, String newPassword, String firstName) {
		boolean isSuccess = false;
		LOG.info("Send forgot password email");
		String[] array = getEmailTemplateTextByTemplateName(TemplateConstant.FORGOT_PASSWORD_EMAIL.getValue());
		if (array != null) {
			try {
				String templateText = array[0];
				String templateSubject = array[1];
				templateText = templateText.replaceAll("##PASSWORD##", newPassword);
				templateText = templateText.replaceAll("##FIRSTNAME##", firstName);
				sendEmail(email, templateText, templateSubject);
				LOG.info("Change Password Email Send SuccessFully to " + email);
				isSuccess = true;
			} catch (Exception e) {
				LOG.info(Arrays.toString(e.getStackTrace()));
				isSuccess = false;
				LOG.info("Change Password Email not Send");
			}
		} else {
			isSuccess = false;
			LOG.info("change Password Email not Send because no email template found");
		}
		
		return isSuccess;
	}

	/**
	 * @param email
	 * @param password
	 * @param userName
	 * @return
	 */
	@Async
	public void sendChangeUserPasswordEmail(String email, String password, String userName) throws Exception {
		LOG.info("Change Password Email");
		String[] array = getEmailTemplateTextByTemplateName(TemplateConstant.CHANGE_USER_PASSWORD_EMAIL.getValue());
		if (array != null) {
			try {
				String templateText = array[0];
				String templateSubject = array[1];
				templateText = templateText.replaceAll("##PASSWORD##", password);
				templateText = templateText.replaceAll("##USERNAME##", username);
				sendEmail(email, templateText, templateSubject);
				LOG.info("change Password Email Send SuccessFully to " + email);
			} catch (Exception e) {
				LOG.info("Change Password Email not Sent 123");
				LOG.info(Arrays.toString(e.getStackTrace()));
				throw e;
			}
		} else {
			LOG.info("Change Password Email not Send because no email template found");
		}
	}

	/**
	 * @param email
	 * @param password
	 * @param userName
	 */
	@Async
	public void sendWelcomeEmail(String email, String password, String userName) {
		String[] array = getEmailTemplateTextByTemplateName(TemplateConstant.USER_REGISTRATION.getValue());
		if (array != null) {
			try {
				String templateText = array[0];
				String templateSubject = array[1];
				templateText = templateText.replaceAll("##PASSWORD##", password);
				templateText = templateText.replaceAll("##USERNAME##", userName);
				sendEmail(email, templateText, templateSubject);
				LOG.info("Welcome email send seccessfully to " + email);
			} catch (Exception e) {
				LOG.info("Welcome email not send " + e);
				LOG.info(Arrays.toString(e.getStackTrace()));
			}
		} else {
			LOG.info("Welcome email not Send because no email template found");
		}
	}

	/**
	 * @param email
	 * @param password
	 * @param userName
	 */
	@Async
	public void sendResetPasswordMailByAdmin(String email, String password, String userName) {
		String[] array = getEmailTemplateTextByTemplateName(TemplateConstant.RESET_PASSWORD.getValue());
		if (array != null) {
			try {
				String templateText = array[0];
				String templateSubject = array[1];
				templateText = templateText.replaceAll("##PASSWORD##", password);
				templateText = templateText.replaceAll("##USERNAME##", userName);
				sendEmail(email, templateText, templateSubject);
				LOG.info("Reset Password email send seccessfully to " + email);
			} catch (Exception e) {
				LOG.info("Reset Password not send " + e);
				LOG.info(Arrays.toString(e.getStackTrace()));
			}
		} else {
			LOG.info("Reset Password Email not Send because no template found");
		}
	}

}
