package com.user.lms.controller;

import com.user.lms.domain.LaborersService;
import com.user.lms.models.LaborerModel;
import com.user.lms.models.LabourDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class LaborersRestApiController {
    @Autowired
    private LaborersService laborersService;

    @GetMapping("/labours/admin")
    public List<LabourDetailsModel> loadLaboursForAdmin(@RequestParam(value = "truck_provider_id",required = false) String truckProviderId, Principal principal){
        return this.laborersService.getAllLabours(truckProviderId);
    }

    @DeleteMapping("/labour/{labourId}")
    private String deleteLaborer(@PathVariable(name = "labourId" ) String id){
        try{
            this.laborersService.deleteUser(Long.parseLong(id));
            return "Delete Successfully!!";
        }
        catch(Exception e){
            return "Delete failed:"+e.getMessage();
        }
    }

    @PostMapping("/labour")
    @ResponseBody
    public LabourDetailsModel addLabour(@RequestBody LabourDetailsModel laborer, Principal principal){
      return   this.laborersService.addLabour(laborer,principal);
    }

    @GetMapping("/labours")
    public List<LabourDetailsModel> getAllLabour(@RequestParam(value = "truckProviderId" ,defaultValue = "",required = false) String id){
        return this.laborersService.getAllLabours(id);
    }

    @PutMapping("/labour/{labourId}")
    public LabourDetailsModel editLabour(@PathVariable(name = "labourId" ) String id, @RequestBody LabourDetailsModel laborer, Principal principal){
        return   this.laborersService.editLabour(id, laborer, principal);
    }

    @GetMapping("/labour/{labourId}")
    public LabourDetailsModel getLaborer(@PathVariable(name = "labourId" ) String id){
        return   this.laborersService.getLabourById(id);
    }

}
