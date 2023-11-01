package com.blubank.doctorappointment.api;


import com.blubank.doctorappointment.entity.Appointment;
import com.blubank.doctorappointment.model.BookAppointmentModel;
import com.blubank.doctorappointment.model.OpenTimeAppointmentModel;
import com.blubank.doctorappointment.service.AppointmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Appointment>> bookAppointment(@RequestBody OpenTimeAppointmentModel model){
        var result = appointmentService.addOpenTime(model);
        return ResponseEntity.ok(result);

    }


}
