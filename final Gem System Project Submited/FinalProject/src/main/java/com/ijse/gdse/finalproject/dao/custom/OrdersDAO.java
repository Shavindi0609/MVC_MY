package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.dto.OrdersDTO;

import java.sql.SQLException;

public interface OrdersDAO extends CrudDAO<OrdersDTO> {

    public String getNextPaymentId() throws SQLException;
}
