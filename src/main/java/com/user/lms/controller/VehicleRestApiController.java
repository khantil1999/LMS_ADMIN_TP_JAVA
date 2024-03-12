package com.user.lms.controller;

import com.user.lms.domain.VehicleListService;
import com.user.lms.models.VehicleDetailsModel;
import com.user.lms.models.VehicleListModel;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class VehicleRestApiController {

    @Autowired
    private VehicleListService vehicleListService;

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
    @DeleteMapping("/vehicle")
    private String deleteVehicle(@RequestParam("vehicleId") String vehicleId){
        try{
            this.vehicleListService.deleteVehicle(Long.parseLong(vehicleId));
            return "Delete Successfully!!";
        }
        catch(Exception e){
            return "Delete failed:"+e.getMessage();
        }
    }
    @DeleteMapping("/deletePhoto")
    private String deletePhoto(@RequestParam("photoId") String photoId){
        try{
            this.vehicleListService.deletePhoto(Long.valueOf(photoId));
            return "Delete Successfully!!";
        }
        catch(Exception e){
            return "Delete failed:"+e.getMessage();
        }
    }

    @GetMapping("/vehicle")
    public VehicleDetailsModel getVehicleDetailsById(@RequestParam("vehicleId") String vehicleId){
        return this.vehicleListService.getVehicleById(vehicleId);
    }
    @PostMapping(value = "/addVehicle",consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addVehicle(@Valid @ModelAttribute VehicleListModel vehicleListModel, @RequestParam("photo") List<MultipartFile> photoFiles,
                             Principal principal) throws IOException {

        System.out.println("this is controller for adding the details");

        System.out.println(vehicleListModel);
        // Save the vehicle and photos to the database
        return vehicleListService.saveVehicle(vehicleListModel,photoFiles,principal,false, null);
    }
    @PutMapping(value = "/editVehicle" ,consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateVehicle(@RequestParam("vehicleId") String vehicleId,
                                                @Valid @ModelAttribute VehicleListModel vehicleListModel,
                                                @RequestParam(value = "photo",required = false) List<MultipartFile> photoFiles,
                                                Principal principal) {
        try {
            // Use your service method to update the vehicle details
            vehicleListService.saveVehicle(vehicleListModel,photoFiles,principal,true,vehicleId);
            return ResponseEntity.ok("Vehicle updated successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating vehicle");
        }
    }

    @GetMapping("/exportVehicle")
    public void loadVehiclePage(Model model,
                                HttpServletResponse response) throws IOException
    {
        System.out.println("export vehicle api is called in backed side---");
        // Get data from the service based on startDate and endDate
        List<VehicleDetailsModel> vehicles= vehicleListService.getAllVehicles();

        Context context = new Context();
        context.setVariable("vehicles", vehicles);
        System.out.println("vehicles: " + vehicles);


        // Create the HTML string with Thymeleaf template for table rendering
        String htmlContent = templateEngine.process("startbootstrap-sb-admin-2-gh-pages/vehicle_template", context);
        System.out.println("HTML Content: " + htmlContent);

        System.out.println("HTML Content for PDF: " + htmlContent);

        // Generate PDF content
        byte[] pdfContent = generatePdfContent(htmlContent);

        System.out.println("PDF Content Length: " + pdfContent.length);

        // Set the response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=vehicle_report.pdf");

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
