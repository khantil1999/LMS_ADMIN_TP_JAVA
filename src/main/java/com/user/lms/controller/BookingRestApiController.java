package com.user.lms.controller;

import com.user.lms.domain.BookingService;
import com.user.lms.entity.Booking;
import com.user.lms.models.BookingAcceptModel;
import com.user.lms.models.BookingDeclineModel;
import com.user.lms.models.BookingModel;
import com.user.lms.models.VehicleDetailsModel;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BookingRestApiController {

    @Autowired
    private BookingService bookingService;

    private static ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

    // Initialize and configure the template resolver
    static {
        templateResolver.setPrefix("templates/");  // Set the path to your Thymeleaf templates
        templateResolver.setSuffix(".html");       // Set the file extension of your templates
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
    }

    // Create a Thymeleaf template engine
    private static TemplateEngine templateEngine = new TemplateEngine();

    static {
        // Set the template resolver for the template engine
        templateEngine.setTemplateResolver(templateResolver);
    }

    @PutMapping("/send-email/{bookingId}")
    public ResponseEntity<String> approveBooking(
            @PathVariable(name = "bookingId") Long bookingId,
            @RequestBody BookingAcceptModel bookingAcceptModel) {
        bookingService.approveBooking(bookingId, bookingAcceptModel.getFuelCharge(), bookingAcceptModel.getTollCharge(),
                bookingAcceptModel.getLabourerCharge(), bookingAcceptModel.getTotalAmount(),bookingAcceptModel.getIsTPApproved());

        return ResponseEntity.ok("Booking approved successfully!");
    }

    @PutMapping("/sendEmail/{bookingId}")
    public ResponseEntity<String> declineBooking( @PathVariable(name = "bookingId") Long bookingId,@RequestBody BookingDeclineModel bookingDeclineModel){
        bookingService.disApproveBooking(bookingId,bookingDeclineModel.getReason(),bookingDeclineModel.getIsTPApproved());

        return ResponseEntity.ok("Booking disapproved successfully!");
    }
    @GetMapping("/bookings")
    List<BookingModel> loadAllBookings() {

        return this.bookingService.getAllBookings();
    }

    @GetMapping("/booking")
    BookingModel getBookingById(@RequestParam("id") String id) {
        System.out.println("Get Booking id in controller---");
        System.out.println("id is--"+id);
        return this.bookingService.getBookingById(Long.parseLong(id));
    }

    @GetMapping("/export")
    public void loadBookingPage(Model model,
                                              @RequestParam(name = "startDate",required = false)String startDate,
                                              @RequestParam(name = "endDate",required = false)String endDate,
                                              HttpServletResponse response) throws IOException
    {
        // Get data from the service based on startDate and endDate
        List<BookingModel> bookings;
        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            bookings = bookingService.getAllBookings(startDate, endDate);
        } else {
            bookings = bookingService.getAllBookings();
        }

        Context context = new Context();
        context.setVariable("bookings", bookings);
        System.out.println("Bookings: " + bookings);


        // Create the HTML string with Thymeleaf template for table rendering
        String htmlContent = templateEngine.process("startbootstrap-sb-admin-2-gh-pages/booking_template", context);
        System.out.println("HTML Content: " + htmlContent);

        System.out.println("HTML Content for PDF: " + htmlContent);

        // Generate PDF content
        byte[] pdfContent = generatePdfContent(htmlContent);

        // Set the response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=booking_report.pdf");

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            // Write the PDF content to the response output stream
            outputStream.write(pdfContent);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }


        /*try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Create an ITextRenderer instance
            ITextRenderer renderer = new ITextRenderer();

            // Set the HTML content to the renderer
            renderer.setDocumentFromString(htmlContent);

            // Layout and render the content
            renderer.layout();
            renderer.createPDF(outputStream);

            // Write the generated PDF to the response output stream
            response.getOutputStream().write(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }*/

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

    // Method to generate Thymeleaf HTML template
//    private String generateHtmlTemplate(Model model) {
//        // Use Thymeleaf template engine to generate HTML
//        Context thymeleafContext = new Context();
//        thymeleafContext.setVariables(model.asMap());
//        return templateEngine.process("startbootstrap-sb-admin-2-gh-pages/booking_template", thymeleafContext);
//    }



}
