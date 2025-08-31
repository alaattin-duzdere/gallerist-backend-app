package com.example.gallerist.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "adress")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Adress extends BaseEntity{

    @Column(name= "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "street")
    private String street;
}
