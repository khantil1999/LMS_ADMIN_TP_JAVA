package com.user.lms.controller;

import com.user.lms.domain.AuthService;
import com.user.lms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {


    @Autowired
    private AuthService authService;

    @GetMapping("/home")
    public String loadHomePage(Model model) {
        model.addAttribute("countUsers", authService.count((long) 1));
        model.addAttribute("countTP", authService.count((long) 3));
        model.addAttribute("countLaborers", authService.count((long) 4));
        return "home";
    }
    @GetMapping("/about")
    public String loadAboutPage() {
        return "about";
    }

    @GetMapping("/service")
    public String loadServicePage() {
        return "service";
    }

    @GetMapping("/contact")
    public String loadContactPage() {
        return "contact";
    }

    @GetMapping("/truckproviderlist")
    public String truckProviderList(Model model){
        List<User> users = authService.findAllUsers((long) 3);
        model.addAttribute("users", users);
        return "truckproviderlist";
    }
    @GetMapping("/vehiclelist")
    public String vehicleList(Model model){
        List<User> users = authService.findAllUsers((long)1);
        model.addAttribute("users", users);
        return "vehiclelist";
    }
    @GetMapping("/laborerslist")
    public String laborersList(Model model){
        List<User> users = authService.findAllUsers((long)4);
        model.addAttribute("users", users);
        return "laborerslist";
    }
    @GetMapping("/history")
    public String history(Model model){
        List<User> users = authService.findAllUsers((long)1);
        model.addAttribute("users", users);
        return "history";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<User> users = authService.findAllUsers((long)1);
        model.addAttribute("users", users);
        return "users";
    }

}
