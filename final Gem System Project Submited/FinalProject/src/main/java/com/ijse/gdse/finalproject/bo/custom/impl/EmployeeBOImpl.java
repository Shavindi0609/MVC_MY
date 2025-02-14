package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.EmployeeBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.EmployeeDAO;
import com.ijse.gdse.finalproject.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public String getNextEmployeeId() throws SQLException {
        return employeeDAO.getNext();
    }

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return employeeDAO.save(employeeDTO);
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException {
        return employeeDAO.getAll();
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws SQLException {
        return employeeDAO.delete(employeeId);
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return employeeDAO.update(employeeDTO);
    }

    @Override
    public int getEmployeeCount() throws SQLException {
        return employeeDAO.getEmployeeCount();
    }

}
