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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
            if (!user.getIsApproved()) {
                throw new UsernameNotFoundException("You are not authorized by the admin to proceed further!");
            }

            // Retrieve user roles from the database
            List<String> userRoles = user.getUserRoles().stream().map(userRoles1 -> userRoles1.getRole().getName()).collect(Collectors.toList());

            // Create a list of GrantedAuthority objects from the user roles
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : userRoles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            // Create and return UserDetails object with the retrieved roles
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .authorities(authorities) // Use authorities() instead of roles()
                    .build();
        } catch (UsernameNotFoundException ex) {
            throw new BadCredentialsException(ex.getMessage());
        }
    }



}