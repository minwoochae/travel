package com.travel.service;

import java.io.IOException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.travel.Dto.MailFormDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor 
public class MailService {

	private JavaMailSender emailSender;
	
	 public void sendMultipleMessage(MailFormDto mailDto) throws MessagingException, IOException {
	    	MimeMessage message = emailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        
	        //메일 제목 설정
	        helper.setSubject(mailDto.getTitle());
	       
	        helper.setText(mailDto.getContent(), false);
	        
	        helper.setFrom(mailDto.getFrom());
	        
	    
	        //수신자 한번에 전송
	        helper.setTo(mailDto.getAddress());
	        emailSender.send(message);
	        log.info("mail multiple send complete.");
	  
	    }
}
