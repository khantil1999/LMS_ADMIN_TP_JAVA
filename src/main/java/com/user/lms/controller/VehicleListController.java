package com.user.lms.controller;

import com.user.lms.domain.VehicleListService;
import com.user.lms.entity.VehicleList;
import com.user.lms.models.LaborerModel;
import com.user.lms.models.VehicleListModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class VehicleListController {

    @Autowired
    private VehicleListService vehicleListService;

    @GetMapping("/vehiclelistTP")
    public String getVehicleList(Model model) {
        List<VehicleList> vehicles = vehicleListService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "vehicleListTP";
    }



    @GetMapping("/addVehicle")
    public String showAddVehicleForm(Model model) {
        VehicleListModel vehicle=new VehicleListModel();
        model.addAttribute("vehicles", vehicle);
        return "addVehicles";
    }

    @PostMapping("/addVehicle")
    public String addVehicle(@Valid @ModelAttribute("vehicles") VehicleListModel vehicleListModel,
                             @RequestParam("photos") List<MultipartFile> photoFiles,
                             Principal principal,BindingResult bindingResult,Model model) throws IOException {


            System.out.println("Ss: "+ photoFiles.size());
            System.out.println(vehicleListModel);
            // Perform server-side validation if needed

            // Save the vehicle and photos to the database
            return vehicleListService.saveVehicle(vehicleListModel,photoFiles,principal,bindingResult,model);

    }
    @GetMapping("/editVehicle/{id}")
    public String getVehicle(@PathVariable("id") String vehicleId,Model model){
        VehicleListModel vehicle = vehicleListService.getVehicleById(vehicleId);
        model.addAttribute("vehicle", vehicle);
        return "editVehicles";
    }

    @PutMapping("/editVehicle/{id}")
    public String editVehicle(@PathVariable("id") String vehicleId,@RequestBody @ModelAttribute("vehicle") VehicleListModel vehicle,
    BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "editVehicles";
        }
        this.vehicleListService.editVehicle(vehicleId,vehicle);
        return "redirect:/vehicleDetails/" + vehicleId;
    }
}
