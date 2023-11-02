package com.blubank.doctorappointment.api;


import com.blubank.doctorappointment.entity.Appointment;
import com.blubank.doctorappointment.model.BookAppointmentModel;
import com.blubank.doctorappointment.model.OpenTimeAppointmentModel;
import com.blubank.doctorappointment.model.PatientModel;
import com.blubank.doctorappointment.service.AppointmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
@Log4j2
public class AppointmentController {

    private final AppointmentService appointmentService ;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/open-time")
    public ResponseEntity<String> bookAppointment(
            @RequestBody OpenTimeAppointmentModel model){
        appointmentService.addOpenTime(model);
        return ResponseEntity.ok("appointment_add_successfully");
    }
    @GetMapping("/booked/{doctorId}")
    public ResponseEntity<List<BookAppointmentModel>> getBookAppointmentOfDoctor(@PathVariable Long doctorId){
        return ResponseEntity.ok(appointmentService.getAppointmentBookedOfDoctor(doctorId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<BookAppointmentModel>> getAppointmentOfDoctor(@PathVariable Long doctorId){
        return ResponseEntity.ok(appointmentService.getAppointmentDoctor(doctorId));
    }

    @PostMapping("/take/{appointmentId}")
    public ResponseEntity<BookAppointmentModel> takeAppointment(@PathVariable Long appointmentId ,
                                                       @RequestBody PatientModel patientModel){

        return ResponseEntity.ok(appointmentService.takeAppointment(patientModel , appointmentId));
    }



}
