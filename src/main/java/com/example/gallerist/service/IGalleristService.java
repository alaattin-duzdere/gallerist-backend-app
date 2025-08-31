package com.example.gallerist.service;

import com.example.gallerist.dto.DtoGallerist;
import com.example.gallerist.dto.DtoGalleristCarIU;
import com.example.gallerist.dto.DtoGalleristIU;

public interface IGalleristService {

    public DtoGallerist saveGallerist(DtoGalleristIU dtoGalleristIU);
}
