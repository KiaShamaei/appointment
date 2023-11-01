package com.blubank.doctorappointment.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookAppointmentModel {
    private Long doctorId;
    private LocalDateTime startTime ;
    private String patientName ;
    private String patientFamily ;
    private String patientMobile ;
}
