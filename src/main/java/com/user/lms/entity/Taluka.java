package com.user.lms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "taluka")
public class Taluka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "taluka_name")
    private String name;

    @Column(name = "post_office_name")
    private String postOfficeName;

    @Column(name = "pin_code")
    private Long pinCode;

    @ManyToOne
    @JoinColumn(name = "district_id",referencedColumnName = "id")
    private District district;


}