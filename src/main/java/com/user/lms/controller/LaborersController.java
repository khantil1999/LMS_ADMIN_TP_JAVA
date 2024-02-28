package com.user.lms.controller;

import com.user.lms.domain.LaborersService;
import com.user.lms.domain.VehicleListService;
import com.user.lms.entity.User;
import com.user.lms.models.LaborerModel;
import com.user.lms.models.VehicleListModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
public class LaborersController {
    @Autowired
    private LaborersService laborersService;

    @Autowired
    private VehicleListService vehicleListService;
    @DeleteMapping("/labour")
    private String deleteLaborer(@RequestParam("userId") String userId){
        try{
            this.laborersService.deleteUser(Long.parseLong(userId));
            return "Delete Successfully!!";
        }
        catch(Exception e){
            return "Delete failed:"+e.getMessage();
        }
    }

    @PostMapping("/labour")
    public String addLabour(@RequestBody LaborerModel laborer){
        this.laborersService.addLabour(laborer);
        return "Added Successfully";
    }

    @GetMapping("/labour")
    public LaborerModel getLabour(@RequestParam("userId") String userId){
        return this.laborersService.getLabourById(userId);
    }

    @PutMapping("/labour")
    public String editLabour(@RequestBody LaborerModel laborer){

        return this.laborersService.editLabour(laborer);
    }



}
