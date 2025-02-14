package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.OrdersBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.CustomerDAO;
import com.ijse.gdse.finalproject.dao.custom.GemDAO;
import com.ijse.gdse.finalproject.dao.custom.OrdersDAO;
import com.ijse.gdse.finalproject.dto.CustomerDTO;
import com.ijse.gdse.finalproject.dto.GemDTO;
import com.ijse.gdse.finalproject.dto.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersBOImpl implements OrdersBO {

    OrdersDAO ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ORDER);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CUSTOMER);
    GemDAO gemDAO = (GemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.GEM);


    @Override
    public String getNextOrderId() throws SQLException {
        return ordersDAO.getNext();
    }

    @Override
    public String getNextPaymentId() throws SQLException {
        return ordersDAO.getNextPaymentId();
    }

    @Override
    public boolean saveOrder(OrdersDTO orderDTO) throws SQLException {
        return ordersDAO.save(orderDTO);
    }

    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException {
        return customerDAO.getAllCustomerIds();
    }

    @Override
    public CustomerDTO findByIdCustomer(String selectedItemId) throws SQLException {
        return customerDAO.findById(selectedItemId);
    }

    @Override
    public ArrayList<String> getAllGemIds() throws SQLException {
        return gemDAO.getAllGemIds();
    }

    @Override
    public GemDTO findByIdGem(String selectedItemId) throws SQLException {
        return gemDAO.findById(selectedItemId);
    }
}
