package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.CustomerBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.CustomerDAO;
import com.ijse.gdse.finalproject.dto.CustomerDTO;
import com.ijse.gdse.finalproject.dto.GemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public String getNextCustomerId() throws SQLException {
        return customerDAO.getNext();
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.save(customerDTO);
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException {
        return customerDAO.getAll();
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.update(customerDTO);
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException {
        return customerDAO.delete(customerId);
    }

    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException {
        return customerDAO.getAllCustomerIds();
    }

    @Override
    public int getCustomerCount() throws SQLException {
        return customerDAO.getCustomerCount();
    }

    @Override
    public CustomerDTO findById(String selectedItemId) throws SQLException {
        return customerDAO.findById(selectedItemId);
    }
}
