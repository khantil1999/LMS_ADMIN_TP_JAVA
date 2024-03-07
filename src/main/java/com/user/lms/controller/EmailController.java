//package com.user.lms.controller;
//// ... (other imports)
//
//import com.user.lms.domain.EmailService;
//import com.user.lms.domain.PdfGenerater;
//import com.user.lms.entity.Booking;
//import com.user.lms.entity.EmailRequest;
//import com.user.lms.entity.User;
//import com.user.lms.repository.BookingRepository;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@RestController
//public class EmailController {
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    @PostMapping("/send-email")
//    public ResponseEntity<String> sendEmail(@RequestParam Long bookingId,
//                                            @RequestParam String fuelCharge,
//                                            @RequestParam String tollCharge,
//                                            @RequestParam String labourerCharge,
//                                            @RequestParam String totalAmount) {
//        try {
//            Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
//
//            if (optionalBooking.isPresent()) {
//                Booking booking = optionalBooking.get();
//                User user = booking.getDriver();
//
//            // Combine charges data for PDF content
//            String chargesDetails = "Fuel Charge: " + fuelCharge +
//                    "\nToll Charge: " + tollCharge +
//                    "\nLabourer Charge: " + labourerCharge +
//                    "\nTotal Amount: " + totalAmount;
//
//            // Generate PDF content
//            byte[] pdfContent = PdfGenerater.generatePdf("Booking Details Placeholder", chargesDetails);
//
//            // Send email with PDF attachment
//            emailService.sendEmailWithAttachment(user.getEmail(), "Booking Approval", "Your booking has been approved!", pdfContent, "BookingDetails.pdf");
//
//            return ResponseEntity.ok("Email sent successfully!");
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email.");
//        }
//    }
//}
