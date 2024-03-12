package com.user.lms.controller;

import com.user.lms.domain.LaborersService;
import com.user.lms.models.LaborerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LaborersController {
    @Autowired
    private LaborersService laborersService;

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
