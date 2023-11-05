package com.blubank.doctorappointment.api;


import com.blubank.doctorappointment.model.BookAppointmentModel;
import com.blubank.doctorappointment.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient")
@Log4j2
@Tag(name = "Patient Rest Apis", description = "Defines endpoints for Patient")
public class PatientController {
    private final PatientService patientService ;

    public PatientController(PatientService patientService) {

        this.patientService = patientService;
    }

    @GetMapping("/booked/{mobile}")
    @Operation(summary = "show booked appointment by patient mobile")
    public List<BookAppointmentModel> findAppointmentByPatientId(@PathVariable String mobile){
        log.info("findAppointmentByPatientId call by mobile : {} " , mobile);
        return patientService.getAppointmentByPatient(mobile);
    }


}
