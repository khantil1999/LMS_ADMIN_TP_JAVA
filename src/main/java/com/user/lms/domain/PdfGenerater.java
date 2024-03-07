//package com.user.lms.domain;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import java.io.ByteArrayOutputStream;
//
//public class PdfGenerater {
//
//    public static byte[] generatePdf(String bookingDetails, String chargesDetails) throws DocumentException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Document document = new Document();
//        PdfWriter.getInstance(document, baos);
//
//        document.open();
//        document.add(new Paragraph("Booking Details: " + bookingDetails));
//        document.add(new Paragraph("Charges Details: " + chargesDetails));
//        document.close();
//
//        return baos.toByteArray();
//    }
//}
