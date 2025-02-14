package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.GemDAO;
import com.ijse.gdse.finalproject.dao.custom.OrderDetailsDAO;
import com.ijse.gdse.finalproject.dto.OrderDetailsDTO;
import com.ijse.gdse.finalproject.dao.SQLUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

   GemDAO gemDAO = new GemDAOImpl();

    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException {
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            boolean isOrderDetailsSaved = save(orderDetailsDTO);
            if (!isOrderDetailsSaved) {
                System.out.println("methanin false eka return wenne ");
                return false;

            }

            boolean isGemUpdated = gemDAO.reduceQty(orderDetailsDTO);
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
    public ArrayList<OrderDetailsDTO> getAll() throws SQLException {
        return null;
    }

    public boolean save(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return SQLUtil.execute(
                "insert into orderdetails values (?,?,?,?)",
                orderDetailsDTO.getOrderId(),
                orderDetailsDTO.getGemId(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getPrice()
        );
    }

    @Override
    public boolean update(OrderDetailsDTO DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }
}

