package com.user.lms.controller;

import com.user.lms.domain.AuthService;
import com.user.lms.domain.BookingService;
import com.user.lms.domain.LaborersService;
import com.user.lms.domain.VehicleListService;
import com.user.lms.entity.User;
import com.user.lms.models.BookingModel;
import com.user.lms.models.ChangePasswordModel;
import com.user.lms.models.LabourDetailsModel;
import com.user.lms.models.VehicleDetailsModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminMainViewController {
    @Autowired
    BookingService bookingService;

    @Autowired
    private VehicleListService vehicleListService;

    @Autowired
    private AuthService authService;

    @Autowired
    private LaborersService laborersService;

    @GetMapping("/home")
    public String loadHomePage(Model model) {
//        model.addAttribute("countUsers", authService.count((long) 1));
//        model.addAttribute("countTP", authService.count((long) 3));
//        model.addAttribute("countLaborers", authService.count((long) 4));
//        model.addAttribute("countBookings",bookingService.countBooking());
        return "home";
    }

    @GetMapping("/changePassword")
    public String loadChangePassword(Model model){
        model.addAttribute("changePassword",new ChangePasswordModel());
        return "changepass";
    }


    @GetMapping("/truckproviderlist")
    public String truckProviderList(Model model){
        List<User> users = authService.findAllUsers((long) 3);
        model.addAttribute("users", users);
        return "truckproviderlist";
    }

    @GetMapping("/users")
    public String users(Model model){
        return "users";
    }

    @GetMapping("/vehiclelist")
    public String getVehicleList(Model model) {
        List<VehicleDetailsModel> vehicles = vehicleListService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "vehiclelist";
    }

    @GetMapping("/laborerslist")
    public String laborersList(Model model){
        List<LabourDetailsModel> labours = laborersService.getAllLabours();
        model.addAttribute("labourers", labours);
        return "laborerslist";
    }

    @GetMapping("/history")
    public String loadHistoryPage(Model model)
    {
        List<BookingModel> bookings=bookingService.getAllBookings();
        model.addAttribute("bookings",bookings);
        return "history";
    }

}
