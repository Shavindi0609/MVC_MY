package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<CustomerDTO> {
    public ArrayList<String> getAllCustomerIds() throws SQLException;
    public CustomerDTO findById(String selectedCusId) throws SQLException;
    public int getCustomerCount() throws SQLException;
}
