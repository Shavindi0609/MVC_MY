package com.ijse.gdse.finalproject.model;

import com.ijse.gdse.finalproject.db.DBConnection;
import com.ijse.gdse.finalproject.dto.OrdersDTO;
import com.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersModel {

    private final OrderDetailsModel orderDetailsModel = new OrderDetailsModel();



    public String getNextOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select order_id from orders order by order_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("O%03d", newIdIndex);
        }

        return "O001";
    }

    public String getNextPaymentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select payment_id from payment order by payment_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }

        return "P001";
    }

    public boolean saveOrder(OrdersDTO orderDTO) throws SQLException {
        System.out.println("clicked");
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false); // Start transaction

        try {
            //save payment
            boolean isPaymentSaved = CrudUtil.execute(
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
            boolean isOrderSaved = CrudUtil.execute(
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
}

