package com.user.lms.controller;

import com.user.lms.domain.AuthService;
import com.user.lms.domain.LaborersService;
import com.user.lms.domain.VehicleListService;
import com.user.lms.entity.User;
import com.user.lms.models.BookingModel;
import com.user.lms.models.LaborerModel;
import com.user.lms.models.VehicleDetailsModel;
import com.user.lms.models.VehicleListModel;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
public class LabourersListTPController {

    @Autowired
    private LaborersService laborersService;

    @Autowired
    private AuthService authService;

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
    @DeleteMapping("/deleteLabour")
    private String deleteLaborer(@RequestParam("userId") String userId){
        try{
            this.laborersService.deleteUser(Long.parseLong(userId));
            return "Delete Successfully!!";
        }
        catch(Exception e){
            return "Delete failed:"+e.getMessage();
        }
    }

    @PostMapping("/addLabour")
    public String addLabour(@RequestBody LaborerModel laborer){
        this.laborersService.addLabour(laborer);
        return "Added Successfully";
    }

    @GetMapping("/getLabour")
    public LaborerModel getLabour(@RequestParam("userId") String userId){
        return this.laborersService.getLabourById(userId);
    }

    @PutMapping("/editLabour")
    public String editLabour(@RequestBody LaborerModel laborer){
        return this.laborersService.editLabour(laborer);
    }
    @GetMapping("/exportLabourer")
    public void loadLabourerPage(Model model,
                                HttpServletResponse response) throws IOException
    {
        // Get data from the service based on startDate and endDate
        List<User> users= authService.findAllUsers(Long.valueOf(4));

        Context context = new Context();
        context.setVariable("users", users);
        System.out.println("labourers: " + users);


        // Create the HTML string with Thymeleaf template for table rendering
        String htmlContent = templateEngine.process("startbootstrap-sb-admin-2-gh-pages/labourer_template", context);
        System.out.println("HTML Content: " + htmlContent);

        System.out.println("HTML Content for PDF: " + htmlContent);

        // Generate PDF content
        byte[] pdfContent = generatePdfContent(htmlContent);

        // Set the response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=labourer_report.pdf");

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            // Write the PDF content to the response output stream
            outputStream.write(pdfContent);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
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
}
