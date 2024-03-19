package com.user.lms.entity;

import com.user.lms.models.LabourDetailsModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "labours")
public class Labour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_provider_id",referencedColumnName = "id")
    private User user;


    public static Labour toEntity(LabourDetailsModel labourDetailsModel) {
        Labour labour = new Labour();
        labour.setFirstName(labourDetailsModel.getFirstName());
        labour.setLastName(labourDetailsModel.getLastName());
        labour.setEmail(labourDetailsModel.getEmail());
        labour.setMobile(labourDetailsModel.getMobile());
        return labour;
    }
}
