package com.user.lms.controller;

import com.user.lms.domain.BookingService;
import com.user.lms.domain.TruckProviderService;
import com.user.lms.domain.UserService;
import com.user.lms.domain.UsersService;
import com.user.lms.models.BookingModel;
import com.user.lms.models.DateWiseBookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminDashboardRestController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private TruckProviderService truckProviderService;


    @Autowired
    private BookingService bookingService;

    @GetMapping("/getTruckProviderCount")
    @ResponseBody
    public int getTruckProviderCount() {
        return this.usersService.getTPCountForAdmin();
    }

    @GetMapping("/getUserCount")
    @ResponseBody
    public int getUserCount() {
        return this.usersService.getUserCountForAdmin();
    }

    @GetMapping("/getLaborerCount")
    @ResponseBody
    public int getLaborerCount() {
        return this.usersService.getLabourCountForAdmin();
    }

    @GetMapping("/getBookingCount")
    @ResponseBody
    public int getBookingCount() {
        return (int) bookingService.countBooking();
    }
    @GetMapping("/loadDateWiseBooking")
    @ResponseBody
    public List<DateWiseBookingModel> getDateWiseBooking() {
        return  bookingService.getDateWiseBooking();
    }
}
