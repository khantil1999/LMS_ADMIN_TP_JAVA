package com.user.lms.controller;

import com.user.lms.domain.AuthService;
import com.user.lms.domain.LaborersService;
import com.user.lms.domain.VehicleListService;
import com.user.lms.entity.User;
import com.user.lms.models.LaborerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LabourersListTPController {

    @Autowired
    private LaborersService laborersService;

    @Autowired
    private VehicleListService vehicleListService;

    @DeleteMapping("/deleteLabour")
    private String deleteLaborer(@RequestParam("userId") String userId){
        try{
            this.laborersService.deleteUser(Long.parseLong(userId));
            return "Delete Successfully!!";
        }
        catch(Exception e){
            return "Delete failed:"+e.getMessage();
        }
    }

    @DeleteMapping("/vehicle")
    private String deleteVehicle(@RequestParam("vehicleId") String vehicleId){
        try{
            this.vehicleListService.deleteVehicle(Long.parseLong(vehicleId));
            return "Delete Successfully!!";
        }
        catch(Exception e){
            return "Delete failed:"+e.getMessage();
        }
    }

    @PostMapping("/addLabour")
    public String addLabour(@RequestBody LaborerModel laborer){
        this.laborersService.addLabour(laborer);
        return "Added Successfully";
    }

    @GetMapping("/getLabour")
    public LaborerModel getLabour(@RequestParam("userId") String userId){
        return this.laborersService.getLabourById(userId);
    }

    @PutMapping("/editLabour")
    public String editLabour(@RequestBody LaborerModel laborer){
        return this.laborersService.editLabour(laborer);
    }

}
