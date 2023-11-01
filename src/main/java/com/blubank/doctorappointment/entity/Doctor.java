package com.blubank.doctorappointment.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String name ;
    private String family ;
    private String address ;
    private String phone ;
    private String field ;
    @OneToMany(fetch=FetchType.LAZY , cascade = CascadeType.ALL , mappedBy = "doctor")
    private Set<Appointment> appointments ;
}
