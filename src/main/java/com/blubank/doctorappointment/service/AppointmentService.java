package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.entity.Appointment;
import com.blubank.doctorappointment.model.BookAppointmentModel;
import com.blubank.doctorappointment.model.OpenTimeAppointmentModel;
import com.blubank.doctorappointment.repository.AppointmentRepository;
import com.blubank.doctorappointment.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository ;
    private final DoctorRepository doctorRepository ;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }


    public List<Appointment> addOpenTime(OpenTimeAppointmentModel model) {

        var startTime = model.getStartTime();
        var endTime = model.getEndTime() ;
        var getStartDate = startTime.toLocalDate();
        var getEndDate = endTime.toLocalDate();
        if(!getEndDate.equals(getStartDate)){
            throw  new RuntimeException("you can set appoint for more than one day");
        }
        var doctor = doctorRepository.findById(model.getDoctorId()).orElseThrow(()->new RuntimeException("doctor not find"));
        List<Appointment> appointmentList = new ArrayList<>();
        while (startTime.isBefore(endTime)) {
            LocalDateTime endTimeForSlot = startTime.plusMinutes(30);
            if (( endTime.equals(endTimeForSlot) || endTime.isAfter(endTimeForSlot)) &&
                    Duration.between(startTime, endTimeForSlot).toMinutes() >= 30) {
                Appointment appointment = new Appointment();
                appointment.setDoctor(doctor);
                appointment.setStartTime(startTime);
                appointment.setEndTime(endTimeForSlot);
                appointmentList.add(appointment);
            }
            startTime = endTimeForSlot;
        }
        appointmentRepository.saveAll(appointmentList);
        return appointmentList ;
    }

}
