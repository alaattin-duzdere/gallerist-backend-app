package com.example.gallerist.service.impl;

import com.example.gallerist.dto.DtoAdress;
import com.example.gallerist.dto.DtoAdressIU;
import com.example.gallerist.exceptions.BaseException;
import com.example.gallerist.exceptions.ErrorMessage;
import com.example.gallerist.exceptions.MessageType;
import com.example.gallerist.model.Adress;
import com.example.gallerist.repository.AdressRepository;
import com.example.gallerist.service.IAdressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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

    @Override
    public DtoAdress getAdressById(Long id) {
        Optional<Adress> optAdress = adressRepository.findById(id);
        if (optAdress.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.No_Record_Exist, "No address found for the provided ID : " + id));
        }
        DtoAdress dtoAdress = new DtoAdress();
        BeanUtils.copyProperties(optAdress.get(), dtoAdress);
        return dtoAdress;
    }

    @Override
    public DtoAdress updateAdress(Long id, DtoAdressIU dtoAdressIU) {
        Optional<Adress> optAdress = adressRepository.findById(id);
        if (optAdress.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.No_Record_Exist, "No address found for the provided ID : " + id));
        }

        Adress adressToUpdate = optAdress.get();
        BeanUtils.copyProperties(dtoAdressIU, adressToUpdate);
        adressToUpdate.setCreateTime(new Date());
        Adress updatedAdress = adressRepository.save(adressToUpdate);

        DtoAdress dtoAdress = new DtoAdress();
        BeanUtils.copyProperties(updatedAdress, dtoAdress);
        return dtoAdress;
    }
}
