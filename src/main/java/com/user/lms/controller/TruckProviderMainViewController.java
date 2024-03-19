package com.user.lms.controller;

import com.user.lms.domain.AuthService;
import com.user.lms.domain.BookingService;
import com.user.lms.domain.VehicleListService;
import com.user.lms.entity.User;
import com.user.lms.models.BookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TruckProviderMainViewController {

    @Autowired
    private AuthService authService;

    @Autowired
    private VehicleListService vehicleListService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/profileTP")
    public String loadTruckProviderTP(Model model){
        return "manageProfileTp";
    }

    @GetMapping("/dashboardTP")
    public String loadDashboardPage(Model model){

        model.addAttribute("countVehicles", vehicleListService.countVehicle());
        model.addAttribute("countLaborers", authService.count((long) 4));
        model.addAttribute("countBookings",bookingService.countBooking());
        return "dashboardTP";
    }



    @GetMapping("/vehiclelistTP")
    public String getVehicleList(Model model) {
        return "vehicleListTP";
    }

    @GetMapping("/addVehicle")
    public String showAddVehicleForm() {
        return "addVehicles";
    }

    @GetMapping("/editVehicle")
    public String showEditVehicleForm(){
        return "editVehicle";
    }


    @GetMapping("/laborerslistTP")
    public String loadLaborersPage(Model model){
        List<User> users = authService.findAllUsers((long)4);
        model.addAttribute("users", users);
        return "laborerslistTP";
    }

    @GetMapping("/bookingTP")
    public String loadBookingPage(Model model)
    {
        List<BookingModel> bookings=bookingService.getAllBookings();
        model.addAttribute("bookings",bookings);
        return "bookingTP";
    }

    @GetMapping("/pastbooking")
    public String showPastBookingPage(Model model) {
        List<BookingModel> bookings=bookingService.getAllBookings();
        model.addAttribute("bookings",bookings);
        return "pastBooking";
    }
    @GetMapping("/allDetails")
    public String showAllDetailsPage(){
        return "viewAlldetails";
    }

}
