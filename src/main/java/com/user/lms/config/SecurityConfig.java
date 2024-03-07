package com.user.lms.config;

import com.user.lms.domain.UserAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig  {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsService)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/about", "/service", "/css/**","/vendor/**","/uploaded-files/**",
                                "/js/**", "/img/**", "/scss/**", "/lib/**", "/contact",
                                "/register", "/register/save", "/verify", "/forgotPassword",
                                "/resetPassword","/truckDetails")
                        .permitAll()
                        .requestMatchers("/home",
                                "/updateApprovalForTruckProvider","/changePassword","/dashboardTP",
                                "vehiclelistTP","/laborerslistTP","/bookingTP","/addVehicle",
                                "/profileTP","/me","/states","/districts","/talukas",
                                "/updateProviderArea", "/providerArea", "/updateProfile",
                                "/getVehicle","/editVehicle",
                                "/history","/laborerslist","/vehiclelist","/truckproviderlist",
                                "/users","/pastbooking","/allDetails","/booking","/bookings","/export"
                                      ).authenticated() // Require authentication for the home page
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler())
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());
        http.csrf().disable();
        return http.build();
    }

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new UserAuthenticationSuccessHandler();
    }

}
