package com.user.lms.controller;

import com.user.lms.domain.AuthService;
import com.user.lms.domain.BookingService;
import com.user.lms.domain.VehicleListService;
import com.user.lms.entity.User;
import com.user.lms.models.BookingModel;
import com.user.lms.models.VehicleDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    BookingService bookingService;
    @Autowired
    private VehicleListService vehicleListService;

    @Autowired
    private AuthService authService;

    @GetMapping("/home")
    public String loadHomePage(Model model) {
        model.addAttribute("countUsers", authService.count((long) 1));
        model.addAttribute("countTP", authService.count((long) 3));
        model.addAttribute("countLaborers", authService.count((long) 4));
        model.addAttribute("countBookings",bookingService.countBooking());
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
    public String getVehicleList(Model model) {
        List<VehicleDetailsModel> vehicles = vehicleListService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "vehiclelist";
    }
    @GetMapping("/laborerslist")
    public String laborersList(Model model){
        List<User> users = authService.findAllUsers((long)4);
        model.addAttribute("users", users);
        return "laborerslist";
    }
    @GetMapping("/history")
    public String loadHistoryPage(Model model, @RequestParam(name = "startDate",required = false)String startDate, @RequestParam(name = "endDate",required = false)String endDate)
    {
        System.out.println("ssss" + startDate + endDate);
        List<BookingModel> bookings = new ArrayList<>();
        if(startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()){
            bookings=bookingService.getAllBookings(startDate, endDate);
        }else{
            bookings=bookingService.getAllBookings();
        }

        model.addAttribute("bookings",bookings);
        return "history";

    }

    @GetMapping("/users")
    public String users(Model model){
        List<User> users = authService.findAllUsers((long)1);
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/profileTP")
    public String loadTruckProviderTP(Model model){
        return "manageProfileTp";
    }

}
