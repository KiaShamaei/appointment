package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.entity.Appointment;
import com.blubank.doctorappointment.model.BookAppointmentModel;
import com.blubank.doctorappointment.model.PatientModel;
import com.blubank.doctorappointment.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final AppointmentRepository appointmentRepository ;

    public PatientService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<BookAppointmentModel> getAppointmentByPatient(String mobile){
      List<Appointment> list=  appointmentRepository.findAllAppointmentByPatient(mobile);
        List<BookAppointmentModel> result = list.stream().map(t->BookAppointmentModel.builder()
                .appointmentId(t.getId())
                .startTime(t.getStartTime())
                .endTime(t.getEndTime())
                .doctorId(t.getDoctor().getId())
                .patientName(t.getPatient().getName())
                .patientFamily(t.getPatient().getFamily())
                .patientMobile(t.getPatient().getPhone())
                .build()).collect(Collectors.toList());
        return result;
    }
}
