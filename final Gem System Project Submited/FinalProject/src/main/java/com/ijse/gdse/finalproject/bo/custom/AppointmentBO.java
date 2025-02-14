package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.entity.Appointment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AppointmentBO extends SuperBO {

     ArrayList<Appointment> getAllAppointment() throws SQLException;
     String getNextAppointmentId() throws SQLException;
     boolean saveAppointment(Appointment appointment) throws SQLException;
     boolean deleteAppointment(String appointmentId) throws SQLException;
     boolean updateAppointment(Appointment appointment) throws SQLException;

}
