package com.blubank.doctorappointment.api;


import com.blubank.doctorappointment.model.DoctorModel;
import com.blubank.doctorappointment.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Doctor Rest Apis", description = "Defines endpoints for doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {

        this.doctorService = doctorService;
    }

    @RequestMapping("/add")
    @Operation(summary = "add a doctor ")
    public ResponseEntity addDoctor(@RequestBody DoctorModel model){
        log.info("addDoctor call by doctor name  : {} and mobile   " , model.getName() ,model.getPhone() );
        doctorService.add(model);
        return ResponseEntity.ok("doctor add " +  model.getName());
    }

    @GetMapping("/all")
    @Operation(summary = "get all doctors ")
    public List<DoctorModel> getAllDoctor (){
        log.info("getAllDoctor call" );
       return doctorService.getAll();
    }
}
