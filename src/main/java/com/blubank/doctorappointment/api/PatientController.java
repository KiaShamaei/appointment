package com.blubank.doctorappointment.api;


import com.blubank.doctorappointment.model.BookAppointmentModel;
import com.blubank.doctorappointment.model.PatientModel;
import com.blubank.doctorappointment.service.PatientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@Log4j2
public class PatientController {
    private final PatientService patientService ;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/booked/{mobile}")
    public List<BookAppointmentModel> findAppointmentByPatientId(@PathVariable String mobile){
        return patientService.getAppointmentByPatient(mobile);
    }


}
