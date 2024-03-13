package com.user.lms.models;

import com.user.lms.entity.Booking;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingModel {

    private Long id;
    private String startDestination;
    private String endDestination;
    private int km;
    private Boolean isLabourer;
    private int noOfLabourers;
    private int fuelCharge;
    private int tollCharge;
    private int labourerCharge;
    private int totalAmount;
    private UserDetailsModel user;
    private UserDetailsModel truckProvider;
    private VehicleDetailsModel vehicles;
    private String declineReason;
    private Boolean isTPApproved;
    private Boolean isCustApproved;
    private Date bookingDate;
    private String goodsType;
    private Boolean isPartialPaymentReceived;
    private Boolean isFullPaymentReceived;
    private String bookingConfirmPaymentImagePath;
    private String status;

    private Long additionalCharges ;

    private String additionalChargesReason;

    public static BookingModel fromEntity(Booking booking) {
        BookingModel bookingModel = new BookingModel();
        bookingModel.setId(booking.getId());
        bookingModel.setStartDestination(booking.getStartDestination());
        bookingModel.setEndDestination(booking.getEndDestination());
        bookingModel.setKm(booking.getKm());
        bookingModel.setIsLabourer(booking.getIsLabourer());
        bookingModel.setNoOfLabourers(booking.getNoOfLabourers());
        bookingModel.setFuelCharge(booking.getFuelCharge());
        bookingModel.setTollCharge(booking.getTollCharge());
        bookingModel.setLabourerCharge(booking.getLabourerCharge());
        bookingModel.setTotalAmount(booking.getTotalAmount());
        bookingModel.setUser(UserDetailsModel.fromEntity(booking.getUser()));
        bookingModel.setTruckProvider(UserDetailsModel.fromEntity(booking.getDriver()));
        bookingModel.setVehicles(VehicleDetailsModel.fromEntity(booking.getVehicleList()));
        bookingModel.setDeclineReason(booking.getDeclineReason());
        bookingModel.setIsTPApproved(booking.getIsTPApproved());
        bookingModel.setIsCustApproved(booking.getIsCustApproved());
        bookingModel.setBookingDate(booking.getBookingDate());
        bookingModel.setGoodsType(booking.getGoodsType());
        bookingModel.setIsFullPaymentReceived(booking.getIsFullPaymentReceived());
        bookingModel.setIsPartialPaymentReceived(booking.getIsPartialPaymentReceived());
        bookingModel.setBookingConfirmPaymentImagePath(booking.getBookingConfirmPaymentImagePath());
        bookingModel.setStatus(booking.getStatus().getStatus());
        bookingModel.setAdditionalCharges(booking.getAdditionalCharges());
        bookingModel.setAdditionalChargesReason(booking.getAdditionalChargesReason());
        return bookingModel;
    }
}
