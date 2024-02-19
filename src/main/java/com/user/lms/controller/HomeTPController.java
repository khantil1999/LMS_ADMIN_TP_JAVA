package com.user.lms.controller;

import com.user.lms.domain.AuthService;
import com.user.lms.domain.VehicleListService;
import com.user.lms.entity.User;
import com.user.lms.entity.VehicleList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeTPController {

    @Autowired
    private AuthService authService;

    @Autowired
    private VehicleListService vehicleListService;

    @GetMapping("/laborerslistTP")
    public String loadLaborersPage(Model model){
        List<User> users = authService.findAllUsers((long)4);
        model.addAttribute("users", users);
        return "laborerslistTP";
    }


    /*@GetMapping("/vehiclelistTP")
    public String loadVehicleListPage(Model model){

        List<VehicleList> vehicles=vehicleListService.findAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "vehiclelistTP";
    }*/
}
