package com.blubank.doctorappointment.api;


import com.blubank.doctorappointment.model.AppointmentModel;
import com.blubank.doctorappointment.model.BookAppointmentModel;
import com.blubank.doctorappointment.model.PatientModel;
import com.blubank.doctorappointment.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
@Log4j2
@Tag(name = "Appointment", description = "api for appointment")
public class AppointmentController {

    private final AppointmentService appointmentService ;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/open-time")
    @Operation(summary = "open time for appointment for a doctor ")
    public ResponseEntity<String> bookAppointment(
            @RequestBody AppointmentModel model){
        log.info("bookAppointment call by appointmentId : {} " , model.getAppointmentId());
        appointmentService.addOpenTime(model);
        return ResponseEntity.ok("appointment_add_successfully");
    }


    @GetMapping("/booked/{doctorId}")
    @Operation(summary = "get list of all booked appointments for a doctor get DoctorId doctorId ")
    public ResponseEntity<List<BookAppointmentModel>> getBookAppointmentOfDoctor(@PathVariable Long doctorId){
        log.info("getBookAppointmentOfDoctor call by doctorId : {} " , doctorId);
        return ResponseEntity.ok(appointmentService.getBookedAppointmentOfDoctor(doctorId));
    }
    

    @GetMapping("/doctor/{doctorId}")
    @Operation(summary = "get list of all doctor appointments for a doctor get DoctorId ")
    public ResponseEntity<List<BookAppointmentModel>> getAppointmentOfDoctor(@PathVariable Long doctorId){
        log.info("getAppointmentOfDoctor call by doctorId : {} " , doctorId);
        return ResponseEntity.ok(appointmentService.getAppointmentDoctor(doctorId));
    }


    @PostMapping("/take/{appointmentId}")
    @Operation(summary = "get appointment by id and set it to patient")
    public ResponseEntity<BookAppointmentModel> takeAppointment(@PathVariable Long appointmentId ,
                                                       @RequestBody @Validated PatientModel patientModel){
        log.info("takeAppointment call by appointmentId : {}  , patientMobile : {} " , appointmentId , patientModel.getPhone() );
        return ResponseEntity.ok(appointmentService.takeAppointment(patientModel , appointmentId));
    }


    @DeleteMapping("/delete/{appointmentId}")
    @Operation(summary = "delete appointment by id ")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long appointmentId){
        log.info("deleteAppointment call by appointmentId : {}  " , appointmentId );
        return appointmentService.deleteAppointment(appointmentId);
    }


    @PostMapping("/list")
    @Operation(summary = "get all free appointment by doctor id ")
    public ResponseEntity<List<AppointmentModel>> getListOfAppointmentRe(AppointmentModel model){
        log.info("getListOfAppointmentRe call by appointmentId : {}  " , model.getAppointmentId() );
      return  ResponseEntity.ok( appointmentService.getAppointmentByDoctorAndDate(model));
    }



}
