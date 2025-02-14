package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {

    public  String getNextEmployeeId() throws SQLException;
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException;
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException;
    public boolean deleteEmployee(String employeeId) throws SQLException;
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException;
    public int getEmployeeCount() throws SQLException;
}
