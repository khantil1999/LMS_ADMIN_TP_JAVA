package com.user.lms.controller;

import com.user.lms.domain.BookingService;
import com.user.lms.domain.UsersService;
import com.user.lms.domain.VehicleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TruckProviderDashboardRestController {

    @Autowired
    private VehicleListService vehicleListService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/getVehicleCount")
    @ResponseBody
    public int getTruckProviderCount() {
        return this.vehicleListService.getVehicleCountForTP();
    }

    @GetMapping("/getLaborerCountForTP")
    @ResponseBody
    public int getLaborerCount() {
        return this.usersService.getLabourCountForTP();
    }

    @GetMapping("/getBookingCountForTP")
    @ResponseBody
    public int getBookingCount() {
        return (int) bookingService.countBooking();
    }

}
