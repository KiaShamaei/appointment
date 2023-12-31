package com.blubank.doctorappointment.repository;


import com.blubank.doctorappointment.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient , Long> {
    @Query("select p from Patient p where p.phone = :phone ")
    Optional<Patient> findByPhone(String phone);
}
