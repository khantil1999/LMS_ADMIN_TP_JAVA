package com.user.lms.controller;

import com.user.lms.domain.AuthService;
import com.user.lms.domain.TruckProviderService;
import com.user.lms.entity.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class TruckProviderController {

    @Autowired
    private TruckProviderService truckProviderService;

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


    @PostMapping("/updateApprovalStatus")
    public String updateApprovalStatus(@RequestParam Long userId, @RequestParam boolean isChecked) {
        try {
            this.truckProviderService.updateApprovalStatus(userId, isChecked);
            return "Update successful";
        } catch (Exception e) {
            return "Update failed: " + e.getMessage();
        }
    }
    @GetMapping("/exportDriver")
    public void loadUserPage(Model model,
                             HttpServletResponse response) throws IOException
    {
        // Get data from the service based on startDate and endDate
        List<User> users= authService.findAllUsers(Long.valueOf(3));

        Context context = new Context();
        context.setVariable("users", users);


        // Create the HTML string with Thymeleaf template for table rendering
        String htmlContent = templateEngine.process("startbootstrap-sb-admin-2-gh-pages/driver_template", context);


        // Generate PDF content
        byte[] pdfContent = generatePdfContent(htmlContent);
        // Set the response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=labourer_report.pdf");

        System.out.println("Response--"+response);
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
