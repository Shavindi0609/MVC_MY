package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.SQLUtil;
import com.ijse.gdse.finalproject.dao.custom.PaymentDAO;
import com.ijse.gdse.finalproject.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public String getNext() throws SQLException {
        ResultSet rst = SQLUtil.execute("select payment_id from payment order by payment_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }

        return "P001";
    }

    @Override
    public ArrayList<Orders> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(Orders DTO) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO payment (payment_id, method, total_amount) VALUES (?, ?, ?)",
                DTO.getPaymentId(),
                DTO.getMethod(),
                DTO.getTotalAmount()
        );
    }

    @Override
    public boolean update(Orders DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }
}
