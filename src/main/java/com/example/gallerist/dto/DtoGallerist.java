package com.example.gallerist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class DtoGallerist extends DtoBase{

    private String firstName;

    private String lastName;

    private DtoAdress adress;
}
