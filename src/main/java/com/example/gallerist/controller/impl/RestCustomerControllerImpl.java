package com.example.gallerist.controller.impl;

import com.example.gallerist.controller.IRestCustomerController;
import com.example.gallerist.controller.RestBaseController;
import com.example.gallerist.controller.RootEntity;
import com.example.gallerist.dto.DtoCustomer;
import com.example.gallerist.dto.DtoCustomerIU;
import com.example.gallerist.service.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/customer")
public class RestCustomerControllerImpl extends RestBaseController implements IRestCustomerController {

    private final ICustomerService customerService;

    public RestCustomerControllerImpl(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/save")
    @Override
    public RootEntity<DtoCustomer> saveCustomer(@Valid @RequestBody DtoCustomerIU dtoCustomerIU) {
        return ok(customerService.saveCustomer(dtoCustomerIU));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoCustomer> getCustomerById(@PathVariable Long id) {
        return ok(customerService.getCustomerById(id));
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoCustomer> updateCustomer(@PathVariable Long id,@RequestBody DtoCustomerIU dtoCustomerIU) {
        return ok(customerService.updateCustomer(id, dtoCustomerIU));
    }
}
