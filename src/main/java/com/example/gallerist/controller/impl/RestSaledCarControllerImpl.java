package com.example.gallerist.controller.impl;

import com.example.gallerist.controller.IRestSaledCarController;
import com.example.gallerist.controller.RestBaseController;
import com.example.gallerist.controller.RootEntity;
import com.example.gallerist.dto.DtoSaledCar;
import com.example.gallerist.dto.DtoSaledCarIU;
import com.example.gallerist.service.ISaledCarService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/saled-car")
public class RestSaledCarControllerImpl extends RestBaseController implements IRestSaledCarController {

    private final ISaledCarService saledCarService;

    public RestSaledCarControllerImpl(ISaledCarService saledCarService) {
        this.saledCarService = saledCarService;
    }

    @PostMapping("/save")
    @Override
    public RootEntity<DtoSaledCar> buyCar(@Valid @RequestBody DtoSaledCarIU dtoSaledCarIU) {
        return ok(saledCarService.buyCar(dtoSaledCarIU));
    }
}
