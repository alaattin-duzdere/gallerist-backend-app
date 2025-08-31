package com.example.gallerist.controller.impl;

import com.example.gallerist.controller.IRestGalleristCarController;
import com.example.gallerist.controller.RestBaseController;
import com.example.gallerist.controller.RootEntity;
import com.example.gallerist.dto.DtoGalleristCar;
import com.example.gallerist.dto.DtoGalleristCarIU;
import com.example.gallerist.service.IGalleristCarService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/gallerist-car")
public class RestGalleristCarController extends RestBaseController implements IRestGalleristCarController {

    private final IGalleristCarService galleristCarService;

    public RestGalleristCarController(IGalleristCarService galleristCarService) {
        this.galleristCarService = galleristCarService;
    }

    @PostMapping("/save")
    @Override
    public RootEntity<DtoGalleristCar> saveGalleristCar(@Valid @RequestBody DtoGalleristCarIU dtoGalleristCarIU) {
        return ok(galleristCarService.saveGalleristCar(dtoGalleristCarIU));
    }
}
