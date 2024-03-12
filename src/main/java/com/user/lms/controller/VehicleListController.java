package com.user.lms.controller;

import com.user.lms.domain.VehicleListService;
import com.user.lms.models.VehicleDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class VehicleListController {

    @Autowired
    private VehicleListService vehicleListService;

    @GetMapping("/vehiclelistTP")
    public String getVehicleList(Model model) {
        List<VehicleDetailsModel> vehicles = vehicleListService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "vehicleListTP";
    }

    @GetMapping("/addVehicle")
    public String showAddVehicleForm() {
        return "addVehicles";
    }

    @GetMapping("/editVehicle")
    public String showEditVehicleForm(){
        return "editVehicle";
    }

}
