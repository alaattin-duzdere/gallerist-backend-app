package com.example.gallerist.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass  // bu klasdan dbde bir tablo olusturulmayacak ancak bu sınıfı extend eden sınıflarda ortak özellikler eklenebilecek
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_time")
    @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE_TIME)
    private Date createTime;

}
