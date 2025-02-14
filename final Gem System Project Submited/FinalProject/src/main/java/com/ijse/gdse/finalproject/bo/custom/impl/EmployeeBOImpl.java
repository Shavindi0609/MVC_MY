package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.EmployeeBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.EmployeeDAO;
import com.ijse.gdse.finalproject.dao.custom.UserDAO;
import com.ijse.gdse.finalproject.entity.Employee;


import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.EMPLOYEE);
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.USER);


    @Override
    public String getNextEmployeeId() throws SQLException {
        return employeeDAO.getNext();
    }

    @Override
    public boolean saveEmployee(Employee employee) throws SQLException {
        return employeeDAO.save(employee);
    }

    @Override
    public ArrayList<Employee> getAllEmployee() throws SQLException {
        return employeeDAO.getAll();
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws SQLException {
        return employeeDAO.delete(employeeId);
    }

    @Override
    public boolean updateEmployee(Employee employee) throws SQLException {
        return employeeDAO.update(employee);
    }

    @Override
    public int getEmployeeCount() throws SQLException {
        return employeeDAO.getEmployeeCount();
    }

    @Override
    public ArrayList<String> getAllUserIds() throws SQLException {
        return userDAO.getAllUserIds();
    }

}
