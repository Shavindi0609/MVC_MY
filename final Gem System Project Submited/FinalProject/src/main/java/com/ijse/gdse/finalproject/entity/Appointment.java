package com.ijse.gdse.finalproject.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Appointment {
    private String appointmentId;
    private String customerId;
    private LocalDate date;
    private LocalTime time;
    private Boolean isAttendance;

}
