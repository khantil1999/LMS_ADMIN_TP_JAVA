package com.user.lms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "truck_provider_area") // Specify the table name if it's different from the default
public class TruckProviderArea {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "truck_providet_id", referencedColumnName = "id") // Corrected column name
    private User truckProvider;

    @ManyToOne
    @JoinColumn(name = "taluka_id", referencedColumnName = "id")
    private Taluka taluka;
}
