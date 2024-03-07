package com.user.lms.controller;

import com.user.lms.domain.BookingService;
import com.user.lms.models.BookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PastBookingController {

    @Autowired
    private BookingService bookingService;
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
