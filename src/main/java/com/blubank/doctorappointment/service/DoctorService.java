package com.blubank.doctorappointment.service;


import com.blubank.doctorappointment.entity.Doctor;
import com.blubank.doctorappointment.model.DoctorModel;
import com.blubank.doctorappointment.repository.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    private final DoctorRepository repository ;
    ;

    public Doctor  add(DoctorModel model){
        Doctor doctor = Doctor.builder()
                .name(model.getName())
                .family(model.getFamily())
                .address(model.getAddress())
                .field(model.getField())
                .build();
       return repository.save(doctor);

    }
}
