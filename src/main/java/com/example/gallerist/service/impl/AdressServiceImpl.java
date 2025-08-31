package com.example.gallerist.service.impl;

import com.example.gallerist.dto.DtoAdress;
import com.example.gallerist.dto.DtoAdressIU;
import com.example.gallerist.model.Adress;
import com.example.gallerist.repository.AdressRepository;
import com.example.gallerist.service.IAdressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class AdressServiceImpl implements IAdressService {

    private final AdressRepository adressRepository;

    public AdressServiceImpl(AdressRepository adressRepository) {
        this.adressRepository = adressRepository;
    }

    public Adress createAdress(DtoAdressIU dtoAdressIU){
        Adress adress = new Adress();
        BeanUtils.copyProperties(dtoAdressIU, adress);
        adress.setCreateTime(new Date());
        return adress;
    }

    @Override
    public DtoAdress saveAdress(DtoAdressIU dtoAdressIU) {
        Adress savedAdress = adressRepository.save(createAdress(dtoAdressIU));

        DtoAdress dtoAdress = new DtoAdress();
        BeanUtils.copyProperties(savedAdress, dtoAdress);

        return dtoAdress;
    }
}
