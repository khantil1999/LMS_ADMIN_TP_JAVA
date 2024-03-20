package com.user.lms.repository;

import com.user.lms.entity.Booking;
import com.user.lms.entity.BookingStatus;
import com.user.lms.models.DateWiseBookingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query(value = "SELECT count(b.id) from booking as b where b.truck_provider_id=?1", nativeQuery = true)
    long countBookings(Long providerId);

    @Query(value = "SELECT count(b.id) from booking as b",nativeQuery = true)
    long countBookingsForAdmin();
    @Modifying
    @Query(value = "UPDATE booking SET fuel_charge = :fuelCharge, toll_charge = :tollCharge, labourer_charge = :labourerCharge, total_amount = :totalAmount, is_tp_approved = :isTPApproved WHERE id = :bookingId", nativeQuery = true)
    void addCharges(@Param("bookingId") Long bookingId, int fuelCharge, int tollCharge,
                    int labourerCharge, int totalAmount, Boolean isTPApproved);

    @Modifying
    @Query(value = "UPDATE booking SET decline_reason = :reason, is_tp_approved = :isTPApproved WHERE id = :bookingId", nativeQuery = true)
    void declineReq(@Param("bookingId") Long bookingId, String reason, Boolean isTPApproved);

    @Query(value = "SELECT * FROM booking where isTPApproved=false", nativeQuery = true)
    Booking fetchAllDetails();

    @Query(value = "SELECT * FROM booking WHERE booking_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Booking> getBookingsByDate(String startDate, String endDate);

    @Query("SELECT b from Booking b where b.driver.id =:id " +
            "and b.isTPApproved =:isTpApproved" +
            " and (b.status =:cancelStatus or b.status =:completeStatus) " +
            "and b.bookingDate BETWEEN :startDate AND :endDate  ")
    List<Booking> getAllBookingByTpWithDate(Long id, Boolean isTpApproved, Date startDate, Date endDate, BookingStatus cancelStatus,BookingStatus completeStatus);

    @Query("SELECT b from Booking b where b.driver.id =:id and b.isTPApproved =:isTpApproved and (b.status =:cancelStatus or b.status =:completeStatus) ")
    List<Booking> getAllBookingByTp(Long id, Boolean isTpApproved,BookingStatus cancelStatus,BookingStatus completeStatus);


    @Query("SELECT b FROM Booking b WHERE b.driver.id = :id " +
            "AND (b.status IS NULL OR (b.status <> :completedStatus AND b.status <> :canceledStatus)) " +
            "AND (:status IS NULL OR b.status = :status) " +
            "AND (:startDate IS NULL OR :endDate IS NULL OR b.bookingDate BETWEEN :startDate AND :endDate)")
    List<Booking> findByDriverAndStatusAndBookingDateBetween(@Param("id") Long driverId,
                                                             @Param("status") BookingStatus status,
                                                             @Param("completedStatus") BookingStatus completedStatus,
                                                             @Param("canceledStatus") BookingStatus canceledStatus,
                                                             @Param("startDate") Date startDate,
                                                             @Param("endDate") Date endDate);


    @Query(value = "SELECT COUNT(booking_date) AS count, DATE(booking_date) AS bookingDate FROM booking GROUP BY DATE(booking_date)",nativeQuery = true)
    List<Object[]> getBookingCountByDateRaw();

    default List<DateWiseBookingModel> getBookingCountByDate() {
        List<Object[]> results = getBookingCountByDateRaw();
        List<DateWiseBookingModel> dateWiseBookingModels = new ArrayList<>();

        for (Object[] result : results) {
            Long count = (Long) result[0];
            Date bookingDate = (Date) result[1];
            dateWiseBookingModels.add(new DateWiseBookingModel(bookingDate,count));
        }

        return dateWiseBookingModels;
    }
    @Query(value = "SELECT COUNT(booking_date) AS count, DATE(booking_date) AS bookingDate FROM booking where truck_provider_id=?1 GROUP BY DATE(booking_date)",nativeQuery = true)
    List<Object[]> getBookingCountByDateRawForTP(Long providerId);
    default List<DateWiseBookingModel> getBookingCountByDateForTP(Long providerId) {
        List<Object[]> results = getBookingCountByDateRawForTP(providerId);
        List<DateWiseBookingModel> dateWiseBookingModels = new ArrayList<>();

        for (Object[] result : results) {
            Long count = (Long) result[0];
            Date bookingDate = (Date) result[1];
            dateWiseBookingModels.add(new DateWiseBookingModel(bookingDate,count));
        }

        return dateWiseBookingModels;
    }
    @Query("SELECT b FROM Booking b WHERE b.driver.id= ?1")
    List<Booking> getAllByTruckProvider(Long id);


}

