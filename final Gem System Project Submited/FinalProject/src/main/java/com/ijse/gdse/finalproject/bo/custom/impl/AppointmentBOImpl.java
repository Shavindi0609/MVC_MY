package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.AppointmentBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.AppointmentDAO;
import com.ijse.gdse.finalproject.dto.AppointmentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentBOImpl implements AppointmentBO {

    AppointmentDAO appointmentDAO = (AppointmentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.APPOINTMENT);

    @Override
    public ArrayList<AppointmentDTO> getAllAppointment() throws SQLException {
        return appointmentDAO.getAll();
    }

    @Override
    public String getNextAppointmentId() throws SQLException {
        return appointmentDAO.getNext();
    }

    @Override
    public boolean saveAppointment(AppointmentDTO appointmentDTO) throws SQLException {
        return appointmentDAO.save(appointmentDTO);
    }

    @Override
    public boolean deleteAppointment(String appointmentId) throws SQLException {
        return appointmentDAO.delete(appointmentId);
    }

    @Override
    public boolean updateAppointment(AppointmentDTO appointmentDTO) throws SQLException {
        return appointmentDAO.update(appointmentDTO);
    }
}
