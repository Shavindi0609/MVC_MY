package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.GemDAO;
import com.ijse.gdse.finalproject.dao.custom.OrderDetailsDAO;
import com.ijse.gdse.finalproject.dao.SQLUtil;
import com.ijse.gdse.finalproject.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

   GemDAO gemDAO = new GemDAOImpl();

    public boolean saveOrderDetailsList(ArrayList<OrderDetails> orderDetailsDTOS) throws SQLException {
        for (OrderDetails orderDetails : orderDetailsDTOS) {
            boolean isOrderDetailsSaved = save(orderDetails);
            if (!isOrderDetailsSaved) {
                System.out.println("methanin false eka return wenne ");
                return false;

            }

            boolean isGemUpdated = gemDAO.reduceQty(orderDetails);
            if (!isGemUpdated) {
                return false;
            }

        }
        return true;
    }

    @Override
    public String getNext() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException {
        return null;
    }

    public boolean save(OrderDetails orderDetails) throws SQLException {
        return SQLUtil.execute(
                "insert into orderdetails values (?,?,?,?)",
                orderDetails.getOrderId(),
                orderDetails.getGemId(),
                orderDetails.getQuantity(),
                orderDetails.getPrice()
        );
    }

    @Override
    public boolean update(OrderDetails DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }
}

