package com.example.gallerist.controller.impl;

import com.example.gallerist.controller.IRestCarController;
import com.example.gallerist.controller.RestBaseController;
import com.example.gallerist.controller.RootEntity;
import com.example.gallerist.dto.DtoCar;
import com.example.gallerist.dto.DtoCarIU;
import com.example.gallerist.service.ICarService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/car")
public class RestCarControllerImpl extends RestBaseController implements IRestCarController {

    private final ICarService carService;

    public RestCarControllerImpl(ICarService carService) {
        this.carService = carService;
    }

    @PostMapping("/save")
    @Override
    public RootEntity<DtoCar> saveCar(@Valid @RequestBody DtoCarIU dtoCarIU) {
        return ok(carService.saveCar(dtoCarIU));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoCar> getCarById(@PathVariable Long id) {
        return ok(carService.getCarById(id));
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoCar> updateCar(@PathVariable Long id,@RequestBody DtoCarIU dtoCarIU) {
        return ok(carService.updateCar(id, dtoCarIU));
    }
}
