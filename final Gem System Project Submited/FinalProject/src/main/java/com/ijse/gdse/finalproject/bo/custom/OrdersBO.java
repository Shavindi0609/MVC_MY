package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.entity.Customer;
import com.ijse.gdse.finalproject.entity.Gem;
import com.ijse.gdse.finalproject.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdersBO extends SuperBO {

    String getNextOrderId() throws SQLException;
    String getNextPaymentId() throws SQLException;
    boolean saveOrder(Orders order) throws SQLException;

    ArrayList<String> getAllCustomerIds() throws SQLException;
    public Customer findByIdCustomer(String selectedItemId) throws SQLException;

    ArrayList<String> getAllGemIds() throws SQLException;
    public Gem findByIdGem(String selectedItemId) throws SQLException;
}
