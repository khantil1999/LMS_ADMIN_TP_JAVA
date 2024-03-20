package com.user.lms.domain;

import com.user.lms.entity.PasswordResetToken;
import com.user.lms.entity.Role;
import com.user.lms.entity.User;
import com.user.lms.entity.UserRoles;
import com.user.lms.models.*;
import com.user.lms.repository.PasswordResetTokenRepository;
import com.user.lms.repository.RoleRepository;
import com.user.lms.repository.UserRepository;
import com.user.lms.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private UserRoleRepository userRoleRepository;


    @Transactional
    public String forgotPassword(@Valid @ModelAttribute("forgotPassword") ForgotPasswordModel forgotPasswordModel,
                                 BindingResult bindingResult, Model model) {
        PasswordResetToken passwordResetToken;
        if (bindingResult.hasErrors()) {
            model.addAttribute("forgotPassword", forgotPasswordModel);
        }
        User user = this.userRepository.findExistingUser(forgotPasswordModel.getEmail());


        if (user != null) {
            this.passwordResetTokenRepository.deleteTokenByUser(user.getId());
            passwordResetToken = new PasswordResetToken();
            passwordResetToken.setUser(user);
            passwordResetToken.setToken(UUID.randomUUID().toString());
            passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));

            PasswordResetToken savedData = this.passwordResetTokenRepository.saveAndFlush(passwordResetToken);
            String message = "To reset your password, please click the link: http://localhost:8091/resetPassword?token=" + savedData.getToken();
            this.emailService.sendEmail(forgotPasswordModel.getEmail(), "Reset Password", message);
        }
        model.addAttribute("successMessage", "Please check you email to reset the password!");
        return "/forgot-password";
    }


    public String passwordResetToken(String token, Model model) {
        PasswordResetToken passwordResetToken = this.passwordResetTokenRepository.findDetailsByToken(token);
        System.out.println();
        if (token == null) {
            model.addAttribute("forgotPassword", new ForgotPasswordModel());
            model.addAttribute("tokenErrorMessage", "Invalid Token!");
            return "forgot-password";
        }
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(passwordResetToken.getExpiryDate(), currentTime);
        System.out.println(duration.toMinutes());
        if (Math.abs(duration.toMinutes()) > 30) {
            model.addAttribute("forgotPassword", new ForgotPasswordModel());
            model.addAttribute("tokenErrorMessage", "Token expired please do forgot password again!");
            return "forgot-password";
        }
        model.addAttribute("passModel", new ResetPasswordModel("", "", passwordResetToken.getUser().getEmail(), passwordResetToken.getUser().getId(), null));
        return "resetpass";
    }


    @Transactional
    public String resetPassword(@Valid @ModelAttribute("passModel") ResetPasswordModel resetPasswordModel,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("passModel", resetPasswordModel);
            return "/resetpass";
        }
        if (!resetPasswordModel.isPasswordAndConfirmPasswordMatch()) {
            model.addAttribute("passModel", resetPasswordModel);
            bindingResult.rejectValue("passwordError", "error", "New Password and confirm password should match!");
            return "/resetpass";
        }
        User user = this.userRepository.findExistingUser(resetPasswordModel.getEmail());
        if (user == null) {
            model.addAttribute("resetPasswordError", "User Not found!");
            return "/resetpass";
        }
        user.setPassword(this.passwordEncoder.encode(resetPasswordModel.getNewPassword()));
        model.addAttribute("resetPasswordSuccess", "Password Reset Successfully!");
        this.userRepository.saveAndFlush(user);
        this.passwordResetTokenRepository.deleteTokenByUser(user.getId());
        return "/resetpass";
    }

    public String verifyUser(@RequestParam("email") String email, Model model, RedirectAttributes redirectAttributes) {
        User user = this.userRepository.findExistingUser(email);
        Map<String, Object> data = new HashMap<>();

        data.put("user", new LoginModel());

        if (user == null) {
            redirectAttributes.addFlashAttribute("verificationErrorMessage", "Sorry, you are not registered  with us!");
            return "redirect:/login";
        }
        user.setIsVerified(true);
        this.userRepository.saveAndFlush(user);
        redirectAttributes.addFlashAttribute("verificationSuccessMessage", "Email verification successful. You can now log in.");
        return "redirect:/login";
    }

    public String saveUser(@Valid @ModelAttribute("user") UserModel userModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

            model.addAttribute("user", userModel);
            return "register";
        }
        if (!userModel.isPasswordAndConfirmPasswordMatch()) {
            bindingResult.rejectValue("confirmPasswordError", "error", "Password and confirm password should match!");
            return "register";
        }
        User user = this.userRepository.findExistingUser(userModel.getEmail());
        if (user != null) {
            bindingResult.rejectValue("emailExistError", "error", "Email already exist!");
            return "register";

        }
        User newUser = new User();
        newUser.setEmail(userModel.getEmail());
        newUser.setFirstName(userModel.getFirstName());
        newUser.setLastName(userModel.getLastName());
        newUser.setMobileNo(userModel.getMobileNo());
        newUser.setPassword(this.passwordEncoder.encode(userModel.getPassword()));
        newUser.setIsVerified(false);
        newUser.setStatus("Active");

        newUser = this.userRepository.saveAndFlush(newUser);

        Role role = this.roleRepository.findByName("TRUCK_PROVIDER");
        UserRoles userRole = new UserRoles();
        userRole.setUser(newUser);
        userRole.setRole(role);
        this.userRoleRepository.saveAndFlush(userRole);
        this.emailService.sendRegistrationEmail(newUser);
        Map<String, Object> data = new HashMap<>();

        data.put("user", new UserModel());
        data.put("successMessage", "Registration successful. Check your email for verification.");

        model.addAllAttributes(data);
        return "/register";
    }

    public List<User> findAllUsers(Long id) {
        List<User> users = userRepository.findAllByRoleId(id);
        return users;
    }

    public long count(Long id) {
        return userRepository.countUsers(id);
    }

    @Transactional
    public String changePassword(@Valid @ModelAttribute("changePassword") ChangePasswordModel changePasswordModel,
                                 BindingResult bindingResult, Model model, Principal principal)
    {
        //boolean isReturnToResetPage=false;
        if (bindingResult.hasErrors())
        {
            model.addAttribute("changePassword", changePasswordModel);
            return "/changepass";
        }
        System.out.println(principal.getName());
        if (principal != null)
        {
            String userEmail = principal.getName();
            System.out.println(userEmail);
            User user = this.userRepository.findExistingUser(userEmail);
            if (user != null) {
                System.out.println("user checking");
                if (passwordEncoder.matches(changePasswordModel.getOldPassword(), user.getPassword()))
                {
                    System.out.println("checking old password from database field");
                    if (!changePasswordModel.getOldPassword().equals(changePasswordModel.getNewPassword()))
                    {
                        System.out.println("checking old and new password different");
                        if (changePasswordModel.isPasswordAndConfirmPasswordMatch())
                        {
                            System.out.println("Checking new and confirm password are same..");
                            user.setPassword(passwordEncoder.encode(changePasswordModel.getNewPassword()));
                            userRepository.save(user);
                            model.addAttribute("successMessage", "Password changed successfully");
                        }
                        else
                        {
                            System.out.println("New and confirm password else part");
                            model.addAttribute("passwordError", "New password and confirm password do not match");
                        }
                    }
                    else
                    {
                        System.out.println("New password diffr else part");
                        model.addAttribute("passwordError", "New password should be different from the old password");
                    }
                }
                else
                {
                    System.out.println("else part for old pass checking");
                    model.addAttribute("passwordError","Old password is incorrect");

                }
            }
            else
            {
                System.out.println("else part of checking user");
                model.addAttribute("passwordError", "User not found with the provided email");
            }
            return "/changepass";

        }
        return "login";
    }
}
