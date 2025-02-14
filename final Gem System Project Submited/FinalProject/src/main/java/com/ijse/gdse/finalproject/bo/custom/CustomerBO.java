package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.dto.CustomerDTO;
import com.ijse.gdse.finalproject.dto.GemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {

     String getNextCustomerId() throws SQLException;
     boolean saveCustomer(CustomerDTO customerDTO) throws SQLException;
     ArrayList<CustomerDTO> getAllCustomer() throws SQLException;
     boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;
     boolean deleteCustomer(String customerId) throws SQLException;
     ArrayList<String> getAllCustomerIds() throws SQLException;
     int getCustomerCount() throws SQLException;
     public CustomerDTO findById(String selectedItemId) throws SQLException;
}
