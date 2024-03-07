package com.user.lms.controller;

import com.user.lms.domain.BookingService;
import com.user.lms.models.BookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/bookingTP")
    public String loadBookingPage(Model model, @RequestParam(name = "startDate",required = false)String startDate, @RequestParam(name = "endDate",required = false)String endDate)
    {
        System.out.println("ssss" + startDate + endDate);
        List<BookingModel> bookings = new ArrayList<>();
        if(startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()){
            bookings=bookingService.getAllBookings(startDate, endDate);
        }else{
            bookings=bookingService.getAllBookings();
        }

        model.addAttribute("bookings",bookings);
        return "bookingTP";

    }

}
