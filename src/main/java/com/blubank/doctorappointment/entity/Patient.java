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
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String name ;
    private String family ;
    private String address ;
    private String phone ;
    @OneToMany(fetch=FetchType.LAZY , cascade = CascadeType.ALL , mappedBy = "patient")
    private Set<Appointment> appointments;
}
