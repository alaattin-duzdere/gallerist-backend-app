package com.example.gallerist.service.impl;

import com.example.gallerist.dto.DtoAdress;
import com.example.gallerist.dto.DtoGallerist;
import com.example.gallerist.dto.DtoGalleristIU;
import com.example.gallerist.exceptions.BaseException;
import com.example.gallerist.exceptions.ErrorMessage;
import com.example.gallerist.exceptions.MessageType;
import com.example.gallerist.model.Adress;
import com.example.gallerist.model.Gallerist;
import com.example.gallerist.repository.AdressRepository;
import com.example.gallerist.repository.GalleristRepository;
import com.example.gallerist.service.IGalleristService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class GalleristserviceImpl implements IGalleristService {

    private final GalleristRepository galleristRepository;
    private final AdressRepository adressRepository;

    public GalleristserviceImpl(GalleristRepository galleristRepository, AdressRepository adressRepository) {
        this.galleristRepository = galleristRepository;
        this.adressRepository = adressRepository;
    }

    private Gallerist createGallerist(DtoGalleristIU dtoGalleristIU) {
        Gallerist gallerist = new Gallerist();
        BeanUtils.copyProperties(dtoGalleristIU,gallerist);
        Optional<Adress> optAdress = adressRepository.findById(dtoGalleristIU.getAdressId());
        if(optAdress.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.No_Record_Exist,dtoGalleristIU.getAdressId().toString()));
        }
        gallerist.setAddress(optAdress.get());
        gallerist.setCreateTime(new Date());
        return gallerist;
    }

    @Override
    public DtoGallerist saveGallerist(DtoGalleristIU dtoGalleristIU) {
        Gallerist savedGallerist = galleristRepository.save(createGallerist(dtoGalleristIU));

        DtoGallerist dtoGallerist = new DtoGallerist();
        BeanUtils.copyProperties(savedGallerist, dtoGallerist);

        DtoAdress dtoAdress = new DtoAdress();
        BeanUtils.copyProperties(savedGallerist.getAddress(), dtoAdress);

        dtoGallerist.setAdress(dtoAdress);

        return dtoGallerist;
    }

    @Override
    public DtoGallerist getGalleristById(Long id) {
        Gallerist gallerist = galleristRepository.findById(id).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.No_Record_Exist, "Not found : " + id)));

        DtoAdress dtoAdress = new DtoAdress();
        BeanUtils.copyProperties(gallerist.getAddress(), dtoAdress);

        DtoGallerist dtoGallerist = new DtoGallerist();
        BeanUtils.copyProperties(gallerist, dtoGallerist);
        dtoGallerist.setAdress(dtoAdress);

        return dtoGallerist;
    }

    @Override
    public DtoGallerist updateGallerist(Long id, DtoGalleristIU dtoGalleristIU) {
        Gallerist gallerist = galleristRepository.findById(id).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.No_Record_Exist, "Not found : " + id)));
        BeanUtils.copyProperties(dtoGalleristIU,gallerist, "id", "createTime");
        gallerist.setAddress(adressRepository.findById(dtoGalleristIU.getAdressId()).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.No_Record_Exist, "Not found : " + dtoGalleristIU.getAdressId()))));
        Gallerist savedGallerist = galleristRepository.save(gallerist);

        DtoAdress dtoAdress = new DtoAdress();
        BeanUtils.copyProperties(savedGallerist.getAddress(), dtoAdress);

        DtoGallerist dtoGallerist = new DtoGallerist();
        BeanUtils.copyProperties(savedGallerist, dtoGallerist);
        dtoGallerist.setAdress(dtoAdress);

        return dtoGallerist;
    }
}
