//package com.lms.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.lms.entity.EmailRequest;
//import com.lms.service.impl.EmailService;
//
//@RestController
//public class EmailController {
//	private final EmailService emailService;
//
//    @Autowired
//    public EmailController(EmailService emailService) {
//        this.emailService = emailService;
//    }
//
//    @PostMapping("/send-email")
//    public String sendEmail(@RequestBody EmailRequest emailRequest) {
//        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
//        return "Email sent successfully!";
//    }
//}
