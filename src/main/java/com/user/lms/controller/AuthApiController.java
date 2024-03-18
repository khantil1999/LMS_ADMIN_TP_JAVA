package com.user.lms.controller;

import com.user.lms.domain.AuthService;
import com.user.lms.entity.User;
import com.user.lms.models.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class AuthApiController {


    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loadLoginPage(Model model, @ModelAttribute("verificationErrorMessage") String verificationErrorMessage,
                                @ModelAttribute("verificationSuccessMessage") String verificationSuccessMessage) {
        model.addAttribute("user", new LoginModel());
        if (verificationErrorMessage != null && !verificationErrorMessage.trim().isEmpty()) {
            model.addAttribute("verificationErrorMessage", verificationErrorMessage);
        } else {
            model.addAttribute("verificationErrorMessage", null);
        }
        if (verificationSuccessMessage != null && !verificationSuccessMessage.isEmpty()) {
            model.addAttribute("verificationSuccessMessage", verificationSuccessMessage);
        } else {
            model.addAttribute("verificationSuccessMessage", null);

        }
        return "login";
    }


    @GetMapping("/register")
    public String loadRegistrationPage(Model model) {
        UserModel user = new UserModel();
        model.addAttribute("user", user);
        return "register";
    }


    @PostMapping("/register/save")
    public String saveUser(@Valid @ModelAttribute("user") UserModel userModel, BindingResult bindingResult, Model model) {
        return this.authService.saveUser(userModel, bindingResult, model);
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("email") String email, Model model, RedirectAttributes redirectAttributes) {
        return this.authService.verifyUser(email, model, redirectAttributes);
    }



    @GetMapping("/changePasswordTP")
    public String loadChangePasswordTP(Model model){
        model.addAttribute("changePassword",new ChangePasswordModel());
        return "changepasstp";
    }

    @GetMapping("/forgotPassword")
    public String loadForgotPassword(Model model) {
        model.addAttribute("forgotPassword", new ForgotPasswordModel());
        return "forgot-password";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@Valid @ModelAttribute("forgotPassword") ForgotPasswordModel forgotPasswordModel,
                                 BindingResult bindingResult, Model model) {
        return this.authService.forgotPassword(forgotPasswordModel, bindingResult, model);
    }


    @GetMapping("/resetPassword")
    public String loadResetPage(@RequestParam("token") String token, Model model) {

        return this.authService.passwordResetToken(token, model);
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@Valid @ModelAttribute("passModel") ResetPasswordModel resetPasswordModel,
                                BindingResult bindingResult, Model model) {
        return this.authService.resetPassword(resetPasswordModel, bindingResult, model);
    }



}
