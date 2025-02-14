package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.dto.EmployeeDTO;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<EmployeeDTO> {

    public int getEmployeeCount() throws SQLException;
    public int getTotCount() throws SQLException;
}
