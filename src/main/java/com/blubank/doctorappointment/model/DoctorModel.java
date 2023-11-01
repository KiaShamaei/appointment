package com.blubank.doctorappointment.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorModel {
    private String name ;
    private String family ;
    private String address ;
    private String phone ;
    private String field ;
}
