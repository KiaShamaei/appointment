package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.entity.Appointment;
import com.blubank.doctorappointment.entity.Patient;
import com.blubank.doctorappointment.model.BookAppointmentModel;
import com.blubank.doctorappointment.model.OpenTimeAppointmentModel;
import com.blubank.doctorappointment.model.PatientModel;
import com.blubank.doctorappointment.repository.AppointmentRepository;
import com.blubank.doctorappointment.repository.DoctorRepository;
import com.blubank.doctorappointment.repository.PatientRepository;
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

    private final PatientRepository patientRepository ;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
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
    public List<BookAppointmentModel>  getAppointmentBookedOfDoctor(Long doctorId){
       List<Appointment> list= appointmentRepository.findAllBookedAppointmentByDoctorId(doctorId);
       List<BookAppointmentModel> result = list.stream().map(t->BookAppointmentModel.builder()
               .appointmentId(t.getId())
               .patientName(t.getPatient().getName())
               .patientFamily(t.getPatient().getFamily())
               .patientMobile(t.getPatient().getPhone())
               .startTime(t.getStartTime())
               .endTime(t.getEndTime())
               .doctorId(t.getDoctor().getId())
               .build()).collect(Collectors.toList());

       return result;
    }
    public List<BookAppointmentModel>  getAppointmentDoctor(Long doctorId){
        List<Appointment> list= appointmentRepository.findAllAppointmentByDoctorId(doctorId);
        List<BookAppointmentModel> result = list.stream().map(t->BookAppointmentModel.builder()
                .appointmentId(t.getId())
                .startTime(t.getStartTime())
                .endTime(t.getEndTime())
                .doctorId(t.getDoctor().getId())
                .build()).collect(Collectors.toList());

        return result;
    }
    //todo add validation for patient model
    public BookAppointmentModel takeAppointment(PatientModel model , Long appointmentId){
        Patient patient = patientRepository.findByPhone(model.getPhone()).orElseGet(
                ()-> {
                    Patient p = new Patient();
                    p.setName(model.getName());
                    p.setFamily(model.getFamily());
                    p.setPhone(model.getPhone());
                    p.setAddress(model.getAddress());
                    return patientRepository.save(p);
                });
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(()->new RuntimeException("appointment not find"));
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
        return BookAppointmentModel.builder()
                .appointmentId(appointmentId)
                .patientName(patient.getName())
                .patientFamily(patient.getFamily())
                .patientMobile(patient.getPhone())
                .startTime(appointment.getStartTime())
                .endTime(appointment.getEndTime())
                .doctorId(appointment.getDoctor().getId()).build();

    }

}
