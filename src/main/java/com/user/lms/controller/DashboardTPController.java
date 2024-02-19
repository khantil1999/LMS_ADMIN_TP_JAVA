package com.user.lms.controller;

import com.user.lms.domain.AuthService;
import com.user.lms.domain.VehicleListService;
import com.user.lms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardTPController {

    @Autowired
    private AuthService authService;

    @Autowired
    private VehicleListService vehicleListService;
    @GetMapping("/dashboardTP")
    public String loadDashboardPage(Model model){

        model.addAttribute("countVehicles", vehicleListService.count());
        model.addAttribute("countLaborers", authService.count((long) 4));
        return "dashboardTP";
    }


}
