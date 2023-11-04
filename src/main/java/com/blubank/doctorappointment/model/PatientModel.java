package com.blubank.doctorappointment.model;


import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
public class PatientModel {
    @NotBlank(message = "نام بیمار اجباری می باشد.")
    private String name ;
    @NotBlank(message = "نام خانوادگی اجباری می باشد.")
    private String family ;
    private String address ;
    @NotBlank(message = "شماره موبایل اجباری می باشد.")
    private String phone ;
}
