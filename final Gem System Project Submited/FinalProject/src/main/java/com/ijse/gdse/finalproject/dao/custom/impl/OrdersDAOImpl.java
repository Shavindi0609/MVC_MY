package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.OrdersDAO;
import com.ijse.gdse.finalproject.db.DBConnection;
import com.ijse.gdse.finalproject.dto.OrdersDTO;
import com.ijse.gdse.finalproject.dao.SQLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersDAOImpl implements OrdersDAO {

    private final OrderDetailsDAOImpl orderDetailsModel = new OrderDetailsDAOImpl();



    public String getNext() throws SQLException {
        ResultSet rst = SQLUtil.execute("select order_id from orders order by order_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("O%03d", newIdIndex);
        }

        return "O001";
    }

    @Override
    public ArrayList<OrdersDTO> getAll() throws SQLException {
        return null;
    }

    public String getNextPaymentId() throws SQLException {
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

    public boolean save(OrdersDTO orderDTO) throws SQLException {
        System.out.println("clicked");
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false); // Start transaction

        try {
            //save payment
            boolean isPaymentSaved = SQLUtil.execute(
                    "INSERT INTO payment (payment_id, method, total_amount) VALUES (?, ?, ?)",
                    orderDTO.getPaymentId(),
                    orderDTO.getMethod(),
                    orderDTO.getTotalAmount()
            );

            if (!isPaymentSaved) {
                connection.rollback();
                return false;
            }
            // Save the order
            boolean isOrderSaved = SQLUtil.execute(
                    "INSERT INTO orders (order_id, customer_id, order_date, payment_id) VALUES (?, ?, ?, ?)",
                    orderDTO.getOrderId(),
                    orderDTO.getCustomerId(),
                    orderDTO.getOrderDate(),
                    orderDTO.getPaymentId()
            );

            if (!isOrderSaved) {
                connection.rollback();
                return false;
            }

            // Save order details
            boolean isOrderDetailListSaved = orderDetailsModel.saveOrderDetailsList(orderDTO.getOrderDetailsDTOS());
            if (!isOrderDetailListSaved) {
                connection.rollback();
                return false;
            }

            // Commit transaction
            connection.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback(); // Roll back on error
            return false;

        } finally {
            connection.setAutoCommit(true); // Restore auto-commit
        }
    }

    @Override
    public boolean update(OrdersDTO DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }
}

