package com.blubank.doctorappointment.api;


import com.blubank.doctorappointment.model.AppointmentModel;
import com.blubank.doctorappointment.model.BookAppointmentModel;
import com.blubank.doctorappointment.model.PatientModel;
import com.blubank.doctorappointment.service.AppointmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    /**
     * set appointment for a doctor every 30 min
     * @param model
     * @return
     */
    @PostMapping("/open-time")
    public ResponseEntity<String> bookAppointment(
            @RequestBody AppointmentModel model){
        appointmentService.addOpenTime(model);
        return ResponseEntity.ok("appointment_add_successfully");
    }

    /**
     * get list of all booked appointments base doctorId
     * @param doctorId
     * @return
     */
    @GetMapping("/booked/{doctorId}")
    public ResponseEntity<List<BookAppointmentModel>> getBookAppointmentOfDoctor(@PathVariable Long doctorId){
        return ResponseEntity.ok(appointmentService.getBookedAppointmentOfDoctor(doctorId));
    }

    /**
     * get list of all doctor appointments base doctorId
     * @param doctorId
     * @return
     */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<BookAppointmentModel>> getAppointmentOfDoctor(@PathVariable Long doctorId){
        return ResponseEntity.ok(appointmentService.getAppointmentDoctor(doctorId));
    }

    /**
     * book appointment to a patient
     * @param appointmentId
     * @param patientModel
     * @return
     */
    @PostMapping("/take/{appointmentId}")
    public ResponseEntity<BookAppointmentModel> takeAppointment(@PathVariable Long appointmentId ,
                                                       @RequestBody @Validated PatientModel patientModel){

        return ResponseEntity.ok(appointmentService.takeAppointment(patientModel , appointmentId));
    }

    /**
     * delete appointment base appointmentId
     * @param appointmentId
     * @return
     */
    @DeleteMapping("/delete/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long appointmentId){
        return appointmentService.deleteAppointment(appointmentId);
    }

    /**
     * get appoint free for doctor in special date
     * @param model
     * @return
     */
    @PostMapping("/list")
    public ResponseEntity<List<AppointmentModel>> getListOfAppointmentRe(AppointmentModel model){
      return  ResponseEntity.ok( appointmentService.getAppointmentByDoctorAndDate(model));
    }



}
