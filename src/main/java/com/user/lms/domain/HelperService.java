package com.user.lms.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HelperService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String encodePassword(String password){
        return  this.passwordEncoder.encode(password);
    }
}
