package com.example.gallerist.controller.impl;

import com.example.gallerist.controller.IRestAdressController;
import com.example.gallerist.controller.RestBaseController;
import com.example.gallerist.controller.RootEntity;
import com.example.gallerist.dto.DtoAdress;
import com.example.gallerist.dto.DtoAdressIU;
import com.example.gallerist.service.IAdressService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/api/address")
public class RestAdressController extends RestBaseController implements IRestAdressController {

    private final IAdressService adressService;

    public RestAdressController(IAdressService adressService) {
        this.adressService = adressService;
    }

    @PostMapping("/save")
    @Override
    public RootEntity<DtoAdress> saveAdress(@Valid @RequestBody DtoAdressIU dtoAdressIU) {
        return ok(adressService.saveAdress(dtoAdressIU));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoAdress> getAdressById(@PathVariable Long id) {
        return ok(adressService.getAdressById(id));
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoAdress> updateAdress(@PathVariable Long id, @RequestBody DtoAdressIU dtoAdressIU) {
        return ok(adressService.updateAdress(id, dtoAdressIU));
    }
}
