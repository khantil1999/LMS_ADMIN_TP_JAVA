//package com.lms.controller;
//
//import com.lms.dto.UserDto;
//import com.lms.entity.User;
//import com.lms.exception.UserNotVerifiedException;
//import com.lms.response.LoginResponse;
//import com.lms.service.UserService;
//import com.lms.service.impl.EmailService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@Controller
//public class AuthController {
//	private final UserService userService;
//	private final EmailService emailService;
//	private final JavaMailSender javaMailSender;
//
//	@Autowired
//	public AuthController(UserService userService, EmailService emailService, JavaMailSender javaMailSender) {
//		this.userService = userService;
//		this.emailService = emailService;
//		this.javaMailSender = javaMailSender;
//	}
//
//
//	// Other mapping methods...
//
//	@GetMapping("/register")
//	public String showRegistrationForm(Model model) {
//		model.addAttribute("user", new UserDto());
//		return "register";
//	}
//
//	@PostMapping("/register/save")
//	public String registration(@Valid @ModelAttribute("user") UserDto userDto,
//							   BindingResult result,
//							   Model model) {
//		if (result.hasErrors()) {
//			// Handle validation errors
//			return "register";
//		}
//
//		userDto.setVerificationCode(UUID.randomUUID().toString());
//		userDto.setVerified(false);
//		userService.saveUser(userDto);
//
//		// Send verification email
//		sendVerificationEmail(userDto.getEmail());
//
//		model.addAttribute("message", "Registration successful. Check your email for verification.");
//		return "register";
//	}
//
//	@GetMapping("/login")
//	public String login(){
//		return  "/login";
//	}
//
//	@PostMapping("/login")
//	@ResponseBody
//	public ResponseEntity<LoginResponse> login(@RequestBody User loginRequest) {
//		System.out.println("sss");
//		try {
//			boolean isLoginSuccessful = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
//
//			if (isLoginSuccessful) {
//				return ResponseEntity.ok(new LoginResponse(true, "Login successful"));
//			} else {
//				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(false, "Invalid email or password"));
//			}
//
//		} catch (UserNotVerifiedException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(false, "User not verified. Please check your email for verification instructions."));
//		}
//	}
//
//	@GetMapping("/verify")
//	public String verifyUser(@RequestParam("email") String email, Model model) {
//		boolean verificationSuccessful = userService.verifyUser(email);
//
//		if (verificationSuccessful) {
//			model.addAttribute("message", "Email verification successful. You can now log in.");
//			return "redirect:/login";
//		} else {
//			model.addAttribute("message", "Invalid verification code.");
//			return "verification-result";
//		}
//	}
//
//	private void sendVerificationEmail(String to) {
//		// Similar to your existing method
//	}
//
//	@GetMapping("/changepassword")
//	public String changePassword() {
//		return "changepassword";
//	}
//
//	@GetMapping("/home")
//	public String home() {
//		return "home";
//	}
//}
