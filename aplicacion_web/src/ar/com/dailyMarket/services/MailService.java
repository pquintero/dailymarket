package ar.com.dailyMarket.services;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ar.com.dailyMarket.ui.BaseAction;

public class MailService {
	
	public void sendMail(String[] emailTo, String msg) {
		try {
			initSendMail(emailTo, msg);
		} catch (InvalidPropertiesFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	protected Session getMailSession() throws NamingException {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		return (Session) envCtx.lookup("mail/Session");
	}
	
	public void initSendMail(String[] emailTo, String msg) throws InvalidPropertiesFormatException, IOException, MessagingException {

		Properties props = new Properties();
		props.loadFromXML(BaseAction.class.getResourceAsStream("/mail/mail-properties.xml"));

		String from = (String) props.get("from");
		String subject = (String) props.get("subject");

    	this.sendMail(props, emailTo, subject, msg, from);
	}
	
	public void sendMail(Properties props, String recipients[], String subject, String message, String from) throws MessagingException {
		final String user = (String) props.get("user");
		final String password = (String) props.get("password"); 
		
	    Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	    	@Override
			protected PasswordAuthentication getPasswordAuthentication() {
	    		return new PasswordAuthentication(user, password);
	    	}
	    });
	
	    InternetAddress addressFrom = new InternetAddress(from);

	    Message msg = new MimeMessage(session);
	
	    InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
	    for (int i = 0; i < recipients.length; i++) {
	        addressTo[i] = new InternetAddress(recipients[i]);
	       
		    msg.setSubject(subject);
		    msg.setContent(message, "text/plain");
		    msg.setFrom(addressFrom);
		    msg.setRecipient(Message.RecipientType.TO, addressTo[i]);
		    Transport.send(msg);
	    }  
	}
}
