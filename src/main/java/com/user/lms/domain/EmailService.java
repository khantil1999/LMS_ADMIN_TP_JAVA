package com.user.lms.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import com.user.lms.entity.Booking;
import com.user.lms.models.BookingModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }

    /*  *//*public void sendBookingConfirmationEmailWithPdfAttachment(String to, String subject, Booking bookingData) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            // Set the attributes for the Thymeleaf template
            Context context = new Context();
            context.setVariable("booking", bookingData);

            // Process the HTML template (assuming 'booking-confirmation.html' is in src/main/resources/templates)
            String htmlContent = templateEngine.process("booking-confirmation", context);

            // Generate PDF from the HTML content
            byte[] pdfContent = generatePdfFromHtml(htmlContent);

            // Attach PDF to the email
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText("Booking confirmation details are attached.");
            helper.addAttachment("booking-confirmation.pdf", "application/pdf");

            // Send email
            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }
*/
    public byte[] generatePdfFromHtml(String htmlContent) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            try {
                renderer.createPDF(outputStream, true);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }

            return outputStream.toByteArray();
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
            return new byte[0];
        }
    }

    public void sendBookingDeclineEmail(String to, String subject, Booking bookingData, byte[] attachment, String attachmentName) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();


        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // Set the attributes for the Thymeleaf template
            Context context = new Context();
            context.setVariable("booking", bookingData);

            // Process the HTML template (assuming 'booking-confirmation.html' is in src/main/resources/templates)
            String htmlContent = templateEngine.process("decline-email-template", context);

            // Set the email content

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.addAttachment(attachmentName, new ByteArrayResource(attachment));

            // Attach the PDF
            javaMailSender.send(message);

            // ... (remaining code)
        } catch (MessagingException e) {
            // ... (handle the exception)
        }
//        catch (DocumentException e) {
//            throw new RuntimeException(e);
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }

    public void sendBookingConfirmationEmail(String to, String subject, Booking bookingData, byte[] attachment, String attachmentName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // Set the attributes for the Thymeleaf template
            Context context = new Context();
            BookingModel bookingModel = BookingModel.fromEntity(bookingData);
            bookingModel.setStartDestination(this.getLabelFromDestination(bookingModel.getStartDestination()));
            bookingModel.setEndDestination(this.getLabelFromDestination(bookingModel.getEndDestination()));
            context.setVariable("booking", bookingModel);


            // Process the HTML template (assuming 'booking-confirmation.html' is in src/main/resources/templates)
            String htmlContent = templateEngine.process("booking-confirmation", context);

            // Set the email content

            // Attach the PDF file

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
//            helper.addAttachment(attachmentName, new ByteArrayResource(attachment));
            // Attach the PDF
            javaMailSender.send(mimeMessage);

            // ... (remaining code)
        } catch (MessagingException e) {
            // ... (handle the exception)
        }

    }

    private byte[] generatePdfContent(String htmlContent) throws DocumentException, IOException {
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

    private String getLabelFromDestination(String destination) {


        ObjectMapper objectMapper = new ObjectMapper();
        try {

            JsonNode jsonNode = objectMapper.readTree(destination);
            return jsonNode.get("label").asText();
        } catch (Exception e) {
            return "";
        }
    }
    public void sendPaymentReceivedSuccessfullyMail(Booking booking){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            Context context = new Context();
            BookingModel bookingModel = BookingModel.fromEntity(booking);
            context.setVariable("booking", bookingModel);


            String htmlContent = templateEngine.process("payment-confirmation", context);

            helper.setTo(booking.getUser().getEmail());
            helper.setSubject("Partial Payment Received Successfully");
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
        }
    }


    public void marekBookingAsCompletedMail(Booking booking){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            Context context = new Context();
            BookingModel bookingModel = BookingModel.fromEntity(booking);
            context.setVariable("booking", bookingModel);


            String htmlContent = templateEngine.process("booking-completed-template", context);

            helper.setTo(booking.getUser().getEmail());
            helper.setSubject("Transport Completed");
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
        }
    }
}
