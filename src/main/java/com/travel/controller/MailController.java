package com.travel.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.travel.Dto.MailFormDto;
import com.travel.service.MailService;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MailController {
	
	private final MailService mailService;
	
	 public MailController(MailService mailService) {
	        this.mailService = mailService;
	    }
		
		@GetMapping("/textMail")
	    public String mailSend() {
			return "textMail";
		}
	    
	    @PostMapping("/mail/send")
	    public String sendMail(MailFormDto mailDto) throws MessagingException, IOException {
	    	mailService.sendMultipleMessage(mailDto);
	        return "result";
	    }
	 
	
	
}
