package com.example.gallerist.controller.impl;

import com.example.gallerist.controller.IRestGalleristController;
import com.example.gallerist.controller.RestBaseController;
import com.example.gallerist.controller.RootEntity;
import com.example.gallerist.dto.DtoGallerist;
import com.example.gallerist.dto.DtoGalleristIU;
import com.example.gallerist.service.IGalleristService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/gallerist")
public class RestGalleristControllerImpl extends RestBaseController implements IRestGalleristController {

    private final IGalleristService galleristService;

    public RestGalleristControllerImpl(IGalleristService galleristService) {
        this.galleristService = galleristService;
    }

    @PostMapping("/save")
    @Override
    public RootEntity<DtoGallerist> saveGallerist(@Valid @RequestBody DtoGalleristIU dtoGalleristIU) {
        return ok(galleristService.saveGallerist(dtoGalleristIU));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoGallerist> getGalleristById(@PathVariable Long id) {
        return ok(galleristService.getGalleristById(id));
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoGallerist> updateGallerist(@PathVariable Long id,@RequestBody DtoGalleristIU dtoGalleristIU) {
        return ok(galleristService.updateGallerist(id, dtoGalleristIU));
    }
}
