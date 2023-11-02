package com.blubank.doctorappointment.model;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookAppointmentModel {
    private Long appointmentId;
    private Long doctorId;
    private LocalDateTime startTime ;
    private LocalDateTime endTime ;
    private String patientName ;
    private String patientFamily ;
    private String patientMobile ;
}
