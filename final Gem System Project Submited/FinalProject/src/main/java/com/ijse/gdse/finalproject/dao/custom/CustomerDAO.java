package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.entity.Customer;


import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer> {
    public ArrayList<String> getAllCustomerIds() throws SQLException;
    public Customer findById(String selectedCusId) throws SQLException;
    public int getCustomerCount() throws SQLException;
}
