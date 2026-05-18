package com.spring.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	
	
	public void sendMail(String to,String subject,String message) {
		try {
			SimpleMailMessage mail=new SimpleMailMessage();
			mail.setTo(to);
			mail.setSubject(subject);
			mail.setText(message);
			mailSender.send(mail);
			log.info("Mail sent Successfully..");

		}
		catch(Exception e) {
			e.printStackTrace();
			log.error("Something went wrong while sending mail");
		}
	}
	

}
