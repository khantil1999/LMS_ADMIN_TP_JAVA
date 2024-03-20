package com.user.lms.controller;

import com.user.lms.domain.BookingService;
import com.user.lms.domain.LaborersService;
import com.user.lms.domain.UsersService;
import com.user.lms.domain.VehicleListService;
import com.user.lms.models.DateWiseBookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class TruckProviderDashboardRestController {

    @Autowired
    private VehicleListService vehicleListService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private LaborersService laborersService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/getVehicleCount")
    @ResponseBody
    public int getTruckProviderCount() {
        return this.vehicleListService.getVehicleCountForTP();
    }

    @GetMapping("/getLaborerCountForTP")
    @ResponseBody
    public int getLaborerCount(Principal principal) {
        return this.laborersService.getLabourCountForTP(principal);
    }

    @GetMapping("/getBookingCountForTP")
    @ResponseBody
    public int getBookingCount() {
        return (int) bookingService.countBooking();
    }

    @GetMapping("/loadDateWiseBookingForTP")
    @ResponseBody
    public List<DateWiseBookingModel> getDateWiseBooking(Principal principal) {

        return  bookingService.getDateWiseBookingForTP(principal);
    }

}
