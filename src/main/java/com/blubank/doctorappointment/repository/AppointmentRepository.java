package com.blubank.doctorappointment.repository;

import com.blubank.doctorappointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query(" select a from Appointment a " +
            " where a.doctor.id = :doctorId " +
            " and a.status = com.blubank.doctorappointment.entity.AppointmentStatus.BOOKED ")
    List<Appointment> findAllBookedAppointmentByDoctorId(Long doctorId);

    @Query(" select a from Appointment a " +
            " where a.doctor.id = :doctorId " +
            " and (:localTime is null or date(a.startTime) = date(:localTime) ) " +
            " and ( a.status = com.blubank.doctorappointment.entity.AppointmentStatus.OPEN " +
            " or a.status = com.blubank.doctorappointment.entity.AppointmentStatus.CANCEL_BY_PATIENT  ) " )
    List<Appointment> findAllAppointmentByDoctorIdAndDate(Long doctorId,LocalDateTime localTime);

    @Query(" select a from  Appointment a " +
            " where a.patient.phone = :mobile " +
            " and a.status = com.blubank.doctorappointment.entity.AppointmentStatus.BOOKED ")
    List<Appointment> findAllAppointmentByPatient(String mobile);

}

