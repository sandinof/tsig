package com.utilities;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator{

	 public PasswordAuthentication getPasswordAuthentication()
	    {
	        return new PasswordAuthentication("georeclamos@gmail.com", "georelcamos1");
	    }
}
