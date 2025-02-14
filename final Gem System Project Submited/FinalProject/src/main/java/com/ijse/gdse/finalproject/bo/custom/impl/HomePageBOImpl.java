package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.HomePageBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.AppointmentDAO;
import com.ijse.gdse.finalproject.dao.custom.CustomerDAO;
import com.ijse.gdse.finalproject.dao.custom.EmployeeDAO;
import com.ijse.gdse.finalproject.dao.custom.SupplierDAO;
import com.ijse.gdse.finalproject.dto.AppointmentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class HomePageBOImpl implements HomePageBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.SUPPLIER);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CUSTOMER);
    AppointmentDAO appointmentDAO = (AppointmentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.APPOINTMENT);
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public int getCustomerCount() throws SQLException {
        return customerDAO.getCustomerCount();
    }

    @Override
    public ArrayList<AppointmentDTO> getAllAppointment() throws SQLException {
        return appointmentDAO.getAll();
    }

    @Override
    public int getTotCount() throws SQLException {
        return employeeDAO.getTotCount();
    }

    @Override
    public int getEmployeeCount() throws SQLException {
        return employeeDAO.getEmployeeCount();
    }

    @Override
    public int getSupplierCount() throws SQLException {
        return supplierDAO.getSupplierCount();
    }
}
