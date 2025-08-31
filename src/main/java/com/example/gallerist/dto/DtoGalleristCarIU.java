package com.example.gallerist.dto;

import com.example.gallerist.model.Car;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoGalleristCarIU {

    @NotNull
    private Long galleristId;

    @NotNull
    private Long carId;
}
