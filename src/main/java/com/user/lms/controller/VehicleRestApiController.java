package com.user.lms.controller;

import com.user.lms.domain.VehicleListService;
import com.user.lms.models.VehicleDetailsModel;
import com.user.lms.models.VehicleListModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
public class VehicleRestApiController {

    @Autowired
    private VehicleListService vehicleListService;


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
    @DeleteMapping("/deletePhoto")
    private String deletePhoto(@RequestParam("photoId") String photoId){
        try{
            this.vehicleListService.deletePhoto(Long.valueOf(photoId));
            return "Delete Successfully!!";
        }
        catch(Exception e){
            return "Delete failed:"+e.getMessage();
        }
    }

    @GetMapping("/vehicle")
    public VehicleDetailsModel getVehicleDetailsById(@RequestParam("vehicleId") String vehicleId){
        return this.vehicleListService.getVehicleById(vehicleId);
    }
    @PostMapping(value = "/addVehicle",consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addVehicle(@Valid @ModelAttribute VehicleListModel vehicleListModel, @RequestParam("photo") List<MultipartFile> photoFiles,
                             Principal principal) throws IOException {

        System.out.println("this is controller for adding the details");

        System.out.println(vehicleListModel);
        // Save the vehicle and photos to the database
        return vehicleListService.saveVehicle(vehicleListModel,photoFiles,principal,false, null);
    }
    @PutMapping(value = "/editVehicle" ,consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateVehicle(@RequestParam("vehicleId") String vehicleId,
                                                @Valid @ModelAttribute VehicleListModel vehicleListModel,
                                                @RequestParam(value = "photo",required = false) List<MultipartFile> photoFiles,
                                                Principal principal) {
        try {
            // Use your service method to update the vehicle details
            vehicleListService.saveVehicle(vehicleListModel,photoFiles,principal,true,vehicleId);
            return ResponseEntity.ok("Vehicle updated successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating vehicle");
        }
    }
}
