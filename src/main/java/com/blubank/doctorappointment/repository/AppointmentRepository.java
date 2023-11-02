package com.blubank.doctorappointment.repository;

import com.blubank.doctorappointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("select a from Appointment a where a.doctor.id = :doctorId and a.patient is not null")
    List<Appointment> findAllBookedAppointmentByDoctorId(Long doctorId);
    @Query("select a from Appointment a where a.doctor.id = :doctorId")
    List<Appointment> findAllAppointmentByDoctorId(Long doctorId);
}

