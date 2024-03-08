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
@Table(name = "village")
public class Village {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "village_id")
    private Long id;

    @Column(name = "village_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "taluka_id")
    private Taluka taluka;

    // Getters and setters
}