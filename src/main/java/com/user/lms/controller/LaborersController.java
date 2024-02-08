package com.user.lms.controller;

import com.user.lms.domain.LaborersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LaborersController {
    @Autowired
    private LaborersService laborersService;

    @PostMapping("/deleteLaborer")
    private String deleteLaborer(@RequestParam Long id){
        try{
            this.laborersService.deleteUser(id);
            return "Delete Successfully!!";
        }
        catch(Exception e){
            return "Delete failed:"+e.getMessage();
        }
    }
}
