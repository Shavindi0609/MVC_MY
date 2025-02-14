package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.CustomerBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.CustomerDAO;
import com.ijse.gdse.finalproject.dao.custom.UserDAO;
import com.ijse.gdse.finalproject.entity.Customer;


import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CUSTOMER);
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.USER);


    @Override
    public String getNextCustomerId() throws SQLException {
        return customerDAO.getNext();
    }

    @Override
    public boolean saveCustomer(Customer customer) throws SQLException {
        return customerDAO.save(customer);
    }

    @Override
    public ArrayList<Customer> getAllCustomer() throws SQLException {
        return customerDAO.getAll();
    }


    @Override
    public boolean updateCustomer(Customer customer) throws SQLException {
        return customerDAO.update(customer);
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException {
        return customerDAO.delete(customerId);
    }

    @Override
    public ArrayList<String> getAllUserIds() throws SQLException {
        return userDAO.getAllUserIds();
    }


    @Override
    public int getCustomerCount() throws SQLException {
        return customerDAO.getCustomerCount();
    }

    @Override
    public Customer findById(String selectedItemId) throws SQLException {
        return customerDAO.findById(selectedItemId);
    }
}
