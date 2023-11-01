package com.blubank.doctorappointment.api;


import com.blubank.doctorappointment.model.DoctorModel;
import com.blubank.doctorappointment.service.DoctorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
@Log4j2
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @RequestMapping("/add")
    public ResponseEntity addDoctor(@RequestBody DoctorModel model){
        doctorService.add(model);
        return ResponseEntity.ok("doctor add " +  model.getName());
    }
    @GetMapping("/all")
    public List<DoctorModel> getAllDoctor (){
       return doctorService.getAll();
    }
}
