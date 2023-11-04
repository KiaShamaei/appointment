package com.blubank.doctorappointment.model;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BookAppointmentModel extends AppointmentModel {
    private String patientName ;
    private String patientFamily ;
    private String patientMobile ;
}
