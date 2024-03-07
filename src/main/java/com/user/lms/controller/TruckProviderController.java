package com.user.lms.controller;

import com.user.lms.domain.AuthService;
import com.user.lms.domain.TruckProviderService;
import com.user.lms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TruckProviderController {

    @Autowired
    private TruckProviderService truckProviderService;

    @PostMapping("/updateApprovalStatus")
    public String updateApprovalStatus(@RequestParam Long userId, @RequestParam boolean isChecked) {
        try {
            this.truckProviderService.updateApprovalStatus(userId, isChecked);
            return "Update successful";
        } catch (Exception e) {
            return "Update failed: " + e.getMessage();
        }
    }
}
