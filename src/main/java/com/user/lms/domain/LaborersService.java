package com.user.lms.domain;

import com.user.lms.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaborersService {

    @Autowired
    private  UserRepository userRepository;

    @Transactional
    public void deleteUser(Long userId){
        this.userRepository.deleteLaborers(userId);
    }
}
