package com.blubank.doctorappointment.service;


import com.blubank.doctorappointment.entity.Doctor;
import com.blubank.doctorappointment.model.DoctorModel;
import com.blubank.doctorappointment.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {
    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    private final DoctorRepository repository;
    ;

    public Doctor add(DoctorModel model) {
        Doctor doctor = Doctor.builder()
                .name(model.getName())
                .family(model.getFamily())
                .address(model.getAddress())
                .field(model.getField())
                .phone(model.getPhone())
                .build();
        return repository.save(doctor);

    }

    public List<DoctorModel> getAll() {
        var list = repository.findAll();
        return list.stream().map(t -> {
          return   DoctorModel.builder()
                    .name(t.getName())
                    .family(t.getFamily())
                    .phone(t.getPhone())
                    .address(t.getAddress())
                    .field(t.getField()).build();
        }).collect(Collectors.toList());
    }
}
