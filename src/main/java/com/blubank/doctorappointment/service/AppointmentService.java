package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.entity.Appointment;
import com.blubank.doctorappointment.entity.AppointmentStatus;
import com.blubank.doctorappointment.entity.Patient;
import com.blubank.doctorappointment.model.AppointmentModel;
import com.blubank.doctorappointment.model.BookAppointmentModel;
import com.blubank.doctorappointment.model.PatientModel;
import com.blubank.doctorappointment.repository.AppointmentRepository;
import com.blubank.doctorappointment.repository.DoctorRepository;
import com.blubank.doctorappointment.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public Optional<Appointment> getAppointmentById(Long appointmentId){
       return appointmentRepository.findById(appointmentId);
    }

    /**
     * delete appointment base appointmentId
     * @param appointmentId
     * @return
     */
    @Transactional
    public ResponseEntity deleteAppointment (Long appointmentId){
        Optional<Appointment>appointmentOptional = this.getAppointmentById(appointmentId);

        if(appointmentOptional.isPresent()
                && appointmentOptional.get().getStatus().equals(AppointmentStatus.OPEN)){
            Appointment appointment = appointmentOptional.get();
            if(appointment.getStatus().equals(AppointmentStatus.BOOKED)){
                return new ResponseEntity<>("Appointment Booked" , HttpStatus.NOT_ACCEPTABLE);
            }
            appointment.setStatus(AppointmentStatus.CANCEL_BY_DOCTOR);
            appointmentRepository.save(appointment);
            return ResponseEntity.ok("Appointment Cancel by Doctor successfully!");
        }else{
            return new ResponseEntity<>("Appointment Not found" , HttpStatus.NOT_FOUND);
        }


    }

    /**
     * set appointment for a doctor every 30 min
     * @param  model
     * @type AppointmentModel
     * @return
     */
    public List<Appointment> addOpenTime(AppointmentModel model) {

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
                appointment.setStatus(AppointmentStatus.OPEN);
                appointmentList.add(appointment);
            }
            startTime = endTimeForSlot;
        }
        appointmentRepository.saveAll(appointmentList);
        return appointmentList ;
    }
    /**
     * get list of all booked appointments base doctorId
     * @param doctorId
     * @return
     */
    public List<BookAppointmentModel> getBookedAppointmentOfDoctor(Long doctorId){
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

    /**
     * get list of all doctor appointments base doctorId
     * @param doctorId
     * @return List<BookAppointmentModel>
     */
    public List<BookAppointmentModel>  getAppointmentDoctor(Long doctorId){
        List<Appointment> list= appointmentRepository.findAllAppointmentByDoctorIdAndDate(doctorId , null);
        List<BookAppointmentModel> result = list.stream().map(t->BookAppointmentModel.builder()
                .appointmentId(t.getId())
                .startTime(t.getStartTime())
                .endTime(t.getEndTime())
                .doctorId(t.getDoctor().getId())
                .build()).collect(Collectors.toList());

        return result;
    }
    /**
     * get appointment free for doctor in special date
     * @param model
     * @return
     */
    public List<AppointmentModel>  getAppointmentByDoctorAndDate(AppointmentModel model){
        List<Appointment> list= appointmentRepository.findAllAppointmentByDoctorIdAndDate(model.getDoctorId() , model.getStartTime());
        List<AppointmentModel> result = list.stream().map(t->BookAppointmentModel.builder()
                .startTime(t.getStartTime())
                .endTime(t.getEndTime())
                .doctorId(t.getDoctor().getId())
                .appointmentId(t.getId())
                .build()).collect(Collectors.toList());

        return result;
    }

    /**
     * book appointment to a patient
     * @param appointmentId
     * @param model
     * @type PatientModel
     * @return BookAppointmentModel
     */
    @Transactional
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
        appointment.setStatus(AppointmentStatus.BOOKED);
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
