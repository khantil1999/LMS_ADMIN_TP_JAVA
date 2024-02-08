package com.user.lms.domain;

import com.user.lms.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TruckProviderService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void updateApprovalStatus(Long userId, Boolean isChecked){
         this.userRepository.approvedDisApprovedTruckProvider(isChecked,userId);
    }
}
