package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.dto.CustomerDTO;
import com.ijse.gdse.finalproject.dto.GemDTO;
import com.ijse.gdse.finalproject.dto.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdersBO extends SuperBO {

    String getNextOrderId() throws SQLException;
    String getNextPaymentId() throws SQLException;
    boolean saveOrder(OrdersDTO orderDTO) throws SQLException;

    ArrayList<String> getAllCustomerIds() throws SQLException;
    public CustomerDTO findByIdCustomer(String selectedItemId) throws SQLException;

    ArrayList<String> getAllGemIds() throws SQLException;
    public GemDTO findByIdGem(String selectedItemId) throws SQLException;
}
