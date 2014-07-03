package com.utilities;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class mandarMail {

	public  mandarMail(){}
	
	public void mandarMailto(String destinatario){
	
	   	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	     

			
			String  d_email = "georeclamos@gmail.com";
			String     d_uname = "georeclamos";
			String     d_password = "georelcamos1";
			String       d_host = "smtp.gmail.com";
					String      d_port  = "587"; //465,587
							String        m_to = destinatario;
									String        m_subject = "Testing";
											String        m_text = "Hey, this is the testing email.";

	
											properties.put("mail.smtp.user", d_email);
			properties.put("mail.smtp.host", d_host);
			properties.put("mail.smtp.port", d_port);
			properties.put("mail.smtp.starttls.enable","true");
			properties.put("mail.smtp.debug", "true");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.socketFactory.port", d_port);
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");

			SMTPAuthenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(properties, auth);
			session.setDebug(true);

			MimeMessage msg = new MimeMessage(session);
			try {
				msg.setText(m_text);
				msg.setSubject(m_subject);
				msg.setFrom(new InternetAddress(d_email));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));

				Transport transport = session.getTransport("smtps");
				transport.connect(d_host, 587, d_uname, d_password);
				transport.sendMessage(msg, msg.getAllRecipients());
				transport.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	
	
	}
}