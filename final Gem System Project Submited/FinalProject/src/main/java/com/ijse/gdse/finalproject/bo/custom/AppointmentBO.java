package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.dto.AppointmentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AppointmentBO extends SuperBO {

     ArrayList<AppointmentDTO> getAllAppointment() throws SQLException;
     String getNextAppointmentId() throws SQLException;
     boolean saveAppointment(AppointmentDTO appointmentDTO) throws SQLException;
     boolean deleteAppointment(String appointmentId) throws SQLException;
     boolean updateAppointment(AppointmentDTO appointmentDTO) throws SQLException;

}
