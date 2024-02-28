package com.user.lms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
@Entity
@Table(name="Photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "truck_provider_id",referencedColumnName = "id")
    private User truckProvider;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleList vehicle;

    private String path;

}
