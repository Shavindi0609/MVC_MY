package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.dto.AppointmentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HomePageBO extends SuperBO {

    int getCustomerCount() throws SQLException;
    ArrayList<AppointmentDTO> getAllAppointment() throws SQLException;
    public int getTotCount() throws SQLException;
    public int getEmployeeCount() throws SQLException;
    public int getSupplierCount() throws SQLException;
}
