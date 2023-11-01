package com.blubank.doctorappointment.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OpenTimeAppointmentModel {
    private Long doctorId;
    private LocalDateTime startTime ;
    private LocalDateTime endTime ;
}
