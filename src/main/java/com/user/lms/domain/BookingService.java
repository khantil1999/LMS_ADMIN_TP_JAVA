package com.user.lms.domain;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import com.user.lms.entity.Booking;
import com.user.lms.entity.BookingStatus;
import com.user.lms.entity.User;
import com.user.lms.entity.VehicleList;
import com.user.lms.expections.BookingNotFoundException;
import com.user.lms.models.*;
import com.user.lms.repository.BookingRepository;
import com.user.lms.repository.UserRepository;
import com.user.lms.repository.VehicleListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookingService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VehicleListRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    private static ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

    // Initialize and configure the template resolver
    static {
        templateResolver.setPrefix("templates/");  // Set the path to your Thymeleaf templates
        templateResolver.setSuffix(".html");       // Set the file extension of your templates
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
    }


    private static TemplateEngine templateEngine = new TemplateEngine();

    static {
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Transactional
    public void approveBooking(Long bookingId, int fuelCharge, int tollCharge, int labourerCharge, int totalAmount, Boolean isTPApproved) {

        try {
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));
                booking.setFuelCharge(fuelCharge);
                booking.setTollCharge(tollCharge);
                booking.setLabourerCharge(labourerCharge);
                booking.setTotalAmount(totalAmount);
                booking.setIsTPApproved(isTPApproved);
                booking.setStatus(BookingStatus.CUSTOMER_PENDING);
            booking = this.bookingRepository.saveAndFlush(booking);
            User user = booking.getUser();
            Context context = new Context();
            BookingModel bookingModel = BookingModel.fromEntity(booking);
            bookingModel.setStartDestination(this.getLabelFromDestination(bookingModel.getStartDestination()));
            bookingModel.setEndDestination(this.getLabelFromDestination(bookingModel.getEndDestination()));
            context.setVariable("booking", bookingModel);

            // Process the Thymeleaf template
            String htmlContent = templateEngine.process("startbootstrap-sb-admin-2-gh-pages/booking-confirmation", context);

            byte[] pdfContent = generatePdfContent(htmlContent); // Replace with your actual PDF generation logic
            emailService.sendBookingConfirmationEmail(user.getEmail(), "Booking Approval", booking, pdfContent, "BookingDetails.pdf");
        } catch (BookingNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void disApproveBooking(Long bookingId, String reason, Boolean isTPApproved) {

        bookingRepository.declineReq(bookingId, reason, isTPApproved);
        try {
            // Fetch the booking information, including the user who made the booking
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));
            User user = booking.getDriver();

            Context context = new Context();
            context.setVariable("booking", booking);

            // Process the Thymeleaf template
            String htmlContent = templateEngine.process("startbootstrap-sb-admin-2-gh-pages/decline-email-template", context);

            byte[] pdfContent = generatePdfContent(htmlContent);

            emailService.sendBookingDeclineEmail(user.getEmail(), "Booking Decline", booking, pdfContent, "BookingDetails.pdf");
        } catch (BookingNotFoundException e) {
            // Handle the exception (e.g., log or throw a custom exception)
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions (e.g., log or throw a custom exception)
            e.printStackTrace();
        }

    }

    private byte[] generatePdfContent(String htmlContent) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
            renderer.finishPDF();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0]; // Placeholder, replace with actual error handling
        }
    }


    public long countBooking() {
        // TODO Auto-generated method stub
        return bookingRepository.countBookings();
    }

    public BookingModel getBookingById(Long id) {
        System.out.println("get Booking by id in service----");
        System.out.println("booking id is---" + id);
        Booking booking = this.bookingRepository.getReferenceById(id);
        return this.mapVehicle(booking);
    }

    private BookingModel mapVehicle(Booking booking) {
        return BookingModel.fromEntity(booking);
    }

    public List<BookingModel> getAllBookings() {
        List<Booking> booking = this.bookingRepository.findAll();
        List<BookingModel> detailsModels = new ArrayList<>();
        booking.forEach(bookings -> {
            BookingModel bookingModel = this.mapVehicle(bookings);
            detailsModels.add(bookingModel);
        });
        return detailsModels;
    }

    public List<BookingModel> getAllBookings(String startDate, String endDate) {

        List<Booking> booking = this.bookingRepository.getBookingsByDate(startDate, endDate);
        List<BookingModel> detailsModels = new ArrayList<>();
        booking.forEach(bookings -> {
            BookingModel bookingModel = this.mapVehicle(bookings);
            detailsModels.add(bookingModel);
        });
        return detailsModels;
    }

    public List<BookingModel> getAllBookingsByTp(Principal principal, String startDate, String endDate,BookingStatus status) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        User user = this.userRepository.findByEmail(principal.getName(), true);
        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            return this.bookingRepository.findByDriverAndStatusAndBookingDateBetween(user.getId(),status, BookingStatus.COMPLETED,BookingStatus.CANCEL, dateFormat.parse(startDate),dateFormat.parse(endDate))
                    .stream().map(BookingModel::fromEntity).collect(Collectors.toList());
        }
        return this.bookingRepository.findByDriverAndStatusAndBookingDateBetween(user.getId(),status, BookingStatus.COMPLETED,BookingStatus.CANCEL, null,null)
                .stream().map(BookingModel::fromEntity).collect(Collectors.toList());
    }

    public List<BookingModel> getAllBookingsByTpWithDate(Principal principal, String startDate, String endDate,Boolean isPast) throws ParseException {
        boolean isTpApproved = false;
        if(isPast){
            isTpApproved = true;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        User user = this.userRepository.findByEmail(principal.getName(), true);
        if (user != null) {
            return this.bookingRepository.getAllBookingByTpWithDate(user.getId(),isTpApproved,  dateFormat.parse(startDate),dateFormat.parse(endDate),BookingStatus.CANCEL,BookingStatus.COMPLETED)
                    .stream().map(BookingModel::fromEntity).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<BookingModel> getAllBookingsByTp(Principal principal,Boolean isPast) {
        boolean isTpApproved = false;
        if(isPast){
            isTpApproved = true;
        }
        User user = this.userRepository.findByEmail(principal.getName(), true);
        if (user != null) {
            return this.bookingRepository.getAllBookingByTp(user.getId(),isTpApproved,BookingStatus.CANCEL,BookingStatus.COMPLETED)
                    .stream().map(BookingModel::fromEntity).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    private String getLabelFromDestination(String destination) {


        ObjectMapper objectMapper = new ObjectMapper();
        try {

            JsonNode jsonNode = objectMapper.readTree(destination);
            return jsonNode.get("label").asText();
        } catch (Exception e) {
            return "";
        }
    }

    public String confirmPaymentBookingByTp(String bookingId){
       Booking booking = this.bookingRepository.getReferenceById(Long.parseLong(bookingId));
       if(booking != null){
           booking.setIsPartialPaymentReceived(true);
           booking.setStatus(BookingStatus.PENDING_PICKUP);
            booking=  this.bookingRepository.saveAndFlush(booking);
           this.emailService.sendPaymentReceivedSuccessfullyMail(booking);
       }
       return "Done";
    }

    public String markBookingAsCompleted(String bookingId,CompleteAndCancelBookingModel completeAndCancelBookingModel){
        Booking booking = this.bookingRepository.getReferenceById(Long.parseLong(bookingId));
        if(booking != null){
            booking.setIsFullPaymentReceived(true);
            booking.setStatus(BookingStatus.COMPLETED);
            if(completeAndCancelBookingModel.getAdditionalCharges() > 0){
                booking.setAdditionalCharges(completeAndCancelBookingModel.getAdditionalCharges());
                long total = booking.getTotalAmount()  + completeAndCancelBookingModel.getAdditionalCharges();
                booking.setTotalAmount((int) total);
                booking.setAdditionalChargesReason(completeAndCancelBookingModel.getAdditionalChargesDetails());
            }else{
                booking.setAdditionalCharges(Long.parseLong("0"));
            }

            booking=  this.bookingRepository.saveAndFlush(booking);
            this.emailService.marekBookingAsCompletedMail(booking);
        }
        return "Done";
    }

    public String cancelBookingByTp(String bookingId,String reason){
        Booking booking = this.bookingRepository.getReferenceById(Long.parseLong(bookingId));
        if(booking != null){
            booking.setIsFullPaymentReceived(false);
            booking.setStatus(BookingStatus.CANCEL);
            booking.setDeclineReason(reason);
            booking=  this.bookingRepository.saveAndFlush(booking);
            this.emailService.cancelByTPMail(booking);
        }
        return "Done";
    }
}

