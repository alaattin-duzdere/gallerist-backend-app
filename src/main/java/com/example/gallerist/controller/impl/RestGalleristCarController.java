package com.example.gallerist.controller.impl;

import com.example.gallerist.controller.IRestGalleristCarController;
import com.example.gallerist.controller.RestBaseController;
import com.example.gallerist.controller.RootEntity;
import com.example.gallerist.dto.DtoGalleristCar;
import com.example.gallerist.dto.DtoGalleristCarIU;
import com.example.gallerist.service.IGalleristCarService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoGalleristCar> getGalleristCarById(@PathVariable Long id) {
        return ok(galleristCarService.getGalleristCarById(id));
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoGalleristCar> updateGalleristCar(@PathVariable Long id,@RequestBody DtoGalleristCarIU dtoGalleristCarIU) {
        return ok(galleristCarService.updateGalleristCar(id, dtoGalleristCarIU));
    }
}
