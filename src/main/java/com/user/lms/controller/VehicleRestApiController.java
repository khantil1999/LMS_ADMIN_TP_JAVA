package com.user.lms.controller;

import com.user.lms.domain.VehicleListService;
import com.user.lms.models.LabourDetailsModel;
import com.user.lms.models.VehicleAddEditRequestModel;
import com.user.lms.models.VehicleDetailsModel;
import com.user.lms.models.VehicleListModel;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VehicleRestApiController {

    @Autowired
    private VehicleListService vehicleListService;

    private static ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

    // Initialize and configure the template resolver
    static {
        templateResolver.setPrefix("templates/");  // Set the path to your Thymeleaf templates
        templateResolver.setSuffix(".html");       // Set the file extension of your templates
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
    }

    // Create a Thymeleaf template engine
    private static TemplateEngine templateEngine = new TemplateEngine();

    static {
        // Set the template resolver for the template engine
        templateEngine.setTemplateResolver(templateResolver);
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
    @PostMapping(value = "/addVehicle")
    public String addVehicle(@RequestBody VehicleAddEditRequestModel vehicleAddEditRequestModel,
                             Principal principal) throws IOException {

        return vehicleListService.saveVehicle(vehicleAddEditRequestModel, principal);
    }
    @PutMapping(value = "/editVehicle" )
    public String updateVehicle(@RequestParam("vehicleId") String vehicleId,
                                                @RequestBody VehicleAddEditRequestModel vehicleAddEditRequestModel,
                                                Principal principal) {
            return vehicleListService.editVehicle(vehicleId, vehicleAddEditRequestModel, principal);
    }


    @GetMapping("/vehiclesByTp")
    public List<VehicleDetailsModel> loadVehiclesByTp(Principal principal){
        return this.vehicleListService.loadVehiclesByTp(principal);
    }

    @GetMapping("/vehicles/admin")
    public List<VehicleDetailsModel> loadVehiclesForAdmin(@RequestParam(value = "truck_provider_id",required = false) String truckProviderId, Principal principal){
        return this.vehicleListService.loadVehiclesForAdmin(truckProviderId,principal);
    }


}
