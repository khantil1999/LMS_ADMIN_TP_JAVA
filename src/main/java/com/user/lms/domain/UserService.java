package com.user.lms.domain;

import com.user.lms.entity.PasswordResetToken;
import com.user.lms.entity.User;
import com.user.lms.models.ForgotPasswordModel;
import com.user.lms.models.ResetPasswordModel;
import com.user.lms.repository.PasswordResetTokenRepository;
import com.user.lms.repository.RoleRepository;
import com.user.lms.repository.UserRepository;
import com.user.lms.repository.UserRoleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {

            User user = this.userRepository.findExistingUser(username);

            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            if (!user.getIsVerified()) {
                throw new UsernameNotFoundException("Please Verify your email first!");
            }
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles("TRUCK_PROVIDER")
                    .build();
        } catch (UsernameNotFoundException ex) {
            throw new BadCredentialsException(ex.getMessage());
        }
    }



}