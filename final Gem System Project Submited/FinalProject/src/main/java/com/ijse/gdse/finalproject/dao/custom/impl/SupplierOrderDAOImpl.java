package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.SupplierOrderDAO;
import com.ijse.gdse.finalproject.dao.custom.SupplierOrderDetailsDAO;
import com.ijse.gdse.finalproject.db.DBConnection;
import com.ijse.gdse.finalproject.dao.SQLUtil;
import com.ijse.gdse.finalproject.entity.SupplierOrder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderDAOImpl implements SupplierOrderDAO {

     SupplierOrderDetailsDAO supplierOrderDetailsDAO = new SupplierOrderDetailsDAOImpl();

    public String getNext() throws SQLException {
        ResultSet rst = SQLUtil.execute("select supplier_order_id from supplierorder order by supplier_order_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring); // 2
            int newIdIndex = i + 1; // 3
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    @Override
    public ArrayList<SupplierOrder> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(SupplierOrder DTO) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO supplierorder (supplier_order_id, supplier_id, order_date, supplier_payment_id) VALUES (?, ?, ?, ?)",
                DTO.getSupplierOrderId(),
                DTO.getSupplierId(),
                DTO.getOrderDate(),
                DTO.getPaymentId()
        );
    }

//    public String getNextSupplierPaymentId() throws SQLException {
//        ResultSet rst = SQLUtil.execute("select supplier_payment_id from supplierpayment order by supplier_payment_id desc limit 1");
//
//        if (rst.next()) {
//            String lastId = rst.getString(1);
//            String substring = lastId.substring(1);
//            int i = Integer.parseInt(substring);
//            int newIdIndex = i + 1;
//            return String.format("Y%03d", newIdIndex);
//        }
//
//        return "Y001";
//    }

//    public boolean save(SupplierOrder supplierOrder) throws SQLException {
//        System.out.println("clicked");
//        Connection connection = DBConnection.getInstance().getConnection();
//        connection.setAutoCommit(false); // Start transaction
//
//        try {
//            //save payment
//            boolean isPaymentSaved = SQLUtil.execute(
//                    "INSERT INTO supplierpayment (supplier_payment_id, method, total_amount) VALUES (?, ?, ?)",
//                    supplierOrder.getPaymentId(),
//                    supplierOrder.getMethod(),
//                    supplierOrder.getTotalAmount()
//            );
//
//            if (!isPaymentSaved) {
//                connection.rollback();
//                return false;
//            }
//            // Save the order
//            boolean isOrderSaved = SQLUtil.execute(
//                    "INSERT INTO supplierorder (supplier_order_id, supplier_id, order_date, supplier_payment_id) VALUES (?, ?, ?, ?)",
//                    supplierOrder.getSupplierOrderId(),
//                    supplierOrder.getSupplierId(),
//                    supplierOrder.getOrderDate(),
//                    supplierOrder.getPaymentId()
//            );
//
//            if (!isOrderSaved) {
//                connection.rollback();
//                return false;
//            }
//
//            // Save order details
//            boolean isSupplierOrderDetailListSaved = supplierOrderDetailsDAO.saveSupplierOrderDetailsList(supplierOrder.getSupplierOrderDetailsDTOS());
//            if (!isSupplierOrderDetailListSaved) {
//                connection.rollback();
//                return false;
//            }
//
//            // Commit transaction
//            connection.commit();
//            return true;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            connection.rollback(); // Roll back on error
//            return false;
//
//        } finally {
//            connection.setAutoCommit(true); // Restore auto-commit
//        }
//    }

    @Override
    public boolean update(SupplierOrder DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }

}

