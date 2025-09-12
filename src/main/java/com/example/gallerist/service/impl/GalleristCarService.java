package com.example.gallerist.service.impl;

import com.example.gallerist.dto.*;
import com.example.gallerist.exceptions.BaseException;
import com.example.gallerist.exceptions.ErrorMessage;
import com.example.gallerist.exceptions.MessageType;
import com.example.gallerist.model.Car;
import com.example.gallerist.model.Gallerist;
import com.example.gallerist.model.GalleristCar;
import com.example.gallerist.repository.CarRepository;
import com.example.gallerist.repository.GalleristCarRepository;
import com.example.gallerist.repository.GalleristRepository;
import com.example.gallerist.service.IGalleristCarService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class GalleristCarService implements IGalleristCarService {

    private final GalleristRepository galleristRepository;
    private final CarRepository carRepository;
    private final GalleristCarRepository galleristCarRepository;

    public GalleristCarService(GalleristRepository galleristRepository, CarRepository carRepository, GalleristCarRepository galleristCarRepository) {
        this.galleristRepository = galleristRepository;
        this.carRepository = carRepository;
        this.galleristCarRepository = galleristCarRepository;
    }

    private GalleristCar createGalleristCar(DtoGalleristCarIU dtoGalleristCarIU) {
        GalleristCar galleristCar = new GalleristCar();
        // validate gallerist exists
        Optional<Gallerist> optGallerist = galleristRepository.findById(dtoGalleristCarIU.getGalleristId());
        if (optGallerist.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.No_Record_Exist,dtoGalleristCarIU.getGalleristId().toString()));
        }
        // validate car exists
        Optional<Car> optCar = carRepository.findById(dtoGalleristCarIU.getCarId());
        if (optCar.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.No_Record_Exist,dtoGalleristCarIU.getCarId().toString()));
        }
        // if both exist, set them to galleristCar
        galleristCar.setGallerist(optGallerist.get());
        galleristCar.setCar(optCar.get());
        galleristCar.setCreateTime(new Date());
        return galleristCar;
    }

    @Override
    public DtoGalleristCar saveGalleristCar(DtoGalleristCarIU dtoGalleristCarIU) {
        GalleristCar savedGalleristCar = galleristCarRepository.save(createGalleristCar(dtoGalleristCarIU));

        DtoGallerist dtoGallerist = new DtoGallerist();
        BeanUtils.copyProperties(savedGalleristCar.getGallerist(),dtoGallerist);

        DtoAdress dtoAdress = new DtoAdress();
        BeanUtils.copyProperties(savedGalleristCar.getGallerist().getAddress(),dtoAdress);
        dtoGallerist.setAdress(dtoAdress);

        DtoCar dtoCar = new DtoCar();
        BeanUtils.copyProperties(savedGalleristCar.getCar(),dtoCar);

        DtoGalleristCar dtoGalleristCar = new DtoGalleristCar();
        dtoGalleristCar.setCreateTime(new Date());
        dtoGalleristCar.setId(savedGalleristCar.getId());
        dtoGalleristCar.setGallerist(dtoGallerist);
        dtoGalleristCar.setCar(dtoCar);

        return dtoGalleristCar;
    }

    @Override
    public DtoGalleristCar getGalleristCarById(Long id) {
        GalleristCar galleristCar = galleristCarRepository.findById(id).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.No_Record_Exist, id.toString())));

        DtoAdress dtoAdress = new DtoAdress();
        BeanUtils.copyProperties(galleristCar.getGallerist().getAddress(),dtoAdress);

        DtoGallerist dtoGallerist = new DtoGallerist();
        BeanUtils.copyProperties(galleristCar.getGallerist(),dtoGallerist);
        dtoGallerist.setAdress(dtoAdress);

        DtoCar dtoCar = new DtoCar();
        BeanUtils.copyProperties(galleristCar.getCar(),dtoCar);

        // create and return DtoGalleristCar
        DtoGalleristCar dtoGalleristCar = new DtoGalleristCar();
        BeanUtils.copyProperties(galleristCar,dtoGalleristCar);
        dtoGalleristCar.setGallerist(dtoGallerist);
        dtoGalleristCar.setCar(dtoCar);

        return dtoGalleristCar;
    }

    @Override
    public DtoGalleristCar updateGalleristCar(Long id, DtoGalleristCarIU dtoGalleristCarIU) {
        GalleristCar galleristCar = galleristCarRepository.findById(id).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.No_Record_Exist, id.toString())));

        galleristCar.setCar(carRepository.findById(dtoGalleristCarIU.getCarId()).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.No_Record_Exist,"No car exists: " + dtoGalleristCarIU.getCarId().toString()))));
        galleristCar.setGallerist(galleristRepository.findById(dtoGalleristCarIU.getGalleristId()).orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.No_Record_Exist,"No gallerist exists: " + dtoGalleristCarIU.getGalleristId().toString()))));

        GalleristCar savedGalleristcar = galleristCarRepository.save(galleristCar);

        return getGalleristCarById(savedGalleristcar.getId());
    }
}
