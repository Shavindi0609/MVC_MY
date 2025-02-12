package com.ijse.gdse.finalproject.dto.tm;

import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class AppointmentTM {
    private String appointmentId;
    private String customerId;
    private LocalDate date;
    private LocalTime time;
    private Boolean isAttendance;

    }

