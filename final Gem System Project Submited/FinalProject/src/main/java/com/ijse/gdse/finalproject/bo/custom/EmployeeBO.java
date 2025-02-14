package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {

    public  String getNextEmployeeId() throws SQLException;
    public boolean saveEmployee(Employee employee) throws SQLException;
    public ArrayList<Employee> getAllEmployee() throws SQLException;
    public boolean deleteEmployee(String employeeId) throws SQLException;
    public boolean updateEmployee(Employee employee) throws SQLException;
    public int getEmployeeCount() throws SQLException;
    ArrayList<String> getAllUserIds() throws SQLException;
}
