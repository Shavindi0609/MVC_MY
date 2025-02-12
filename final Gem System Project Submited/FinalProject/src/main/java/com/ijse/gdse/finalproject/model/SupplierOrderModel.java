package com.ijse.gdse.finalproject.model;

import com.ijse.gdse.finalproject.db.DBConnection;
import com.ijse.gdse.finalproject.dto.SupplierOrderDTO;
import com.ijse.gdse.finalproject.dto.SupplierOrderDetailsDTO;
import com.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierOrderModel {

    private final SupplierOrderDetailsModel supplierOrderDetailsModel = new SupplierOrderDetailsModel();

    public String getNextSupplierOrderId() throws SQLException {
        // @rst: ResultSet from the query fetching the last order ID from the orders table
        ResultSet rst = CrudUtil.execute("select supplier_order_id from supplierorder order by supplier_order_id desc limit 1");

        if (rst.next()) {
            // @lastId: Retrieves the last order ID
            String lastId = rst.getString(1); // e.g., "O002"
            // @substring: Extracts the numeric part from the order ID
            String substring = lastId.substring(1); // e.g., "002"
            // @i: Converts the numeric part to an integer
            int i = Integer.parseInt(substring); // 2
            // @newIdIndex: Increments the numeric part by 1
            int newIdIndex = i + 1; // 3
            // Returns the new order ID, formatted as "O" followed by a 3-digit number (e.g., O003)
            return String.format("S%03d", newIdIndex);
        }
        // Returns "O001" if no previous orders are found
        return "S001";
    }

    public String getNextSupplierPaymentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select supplier_payment_id from supplierpayment order by supplier_payment_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("Y%03d", newIdIndex);
        }

        return "Y001";
    }

    public boolean saveSupplierOrder(SupplierOrderDTO supplierOrderDTO) throws SQLException {
        System.out.println("clicked");
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false); // Start transaction

        try {
            //save payment
            boolean isPaymentSaved = CrudUtil.execute(
                    "INSERT INTO supplierpayment (supplier_payment_id, method, total_amount) VALUES (?, ?, ?)",
                    supplierOrderDTO.getPaymentId(),
                    supplierOrderDTO.getMethod(),
                    supplierOrderDTO.getTotalAmount()
            );

            if (!isPaymentSaved) {
                connection.rollback();
                return false;
            }
            // Save the order
            boolean isOrderSaved = CrudUtil.execute(
                    "INSERT INTO supplierorder (supplier_order_id, supplier_id, order_date, supplier_payment_id) VALUES (?, ?, ?, ?)",
                    supplierOrderDTO.getSupplierOrderId(),
                    supplierOrderDTO.getSupplierId(),
                    supplierOrderDTO.getOrderDate(),
                    supplierOrderDTO.getPaymentId()
            );

            if (!isOrderSaved) {
                connection.rollback();
                return false;
            }

            // Save order details
            boolean isSupplierOrderDetailListSaved = supplierOrderDetailsModel.saveSupplierOrderDetailsList(supplierOrderDTO.getSupplierOrderDetailsDTOS());
            if (!isSupplierOrderDetailListSaved) {
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

