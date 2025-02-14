package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.SupplierOrderBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.SQLUtil;
import com.ijse.gdse.finalproject.dao.custom.*;
import com.ijse.gdse.finalproject.db.DBConnection;
import com.ijse.gdse.finalproject.entity.Gem;
import com.ijse.gdse.finalproject.entity.Supplier;
import com.ijse.gdse.finalproject.entity.SupplierOrder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderBOImpl implements SupplierOrderBO {

    SupplierOrderDAO supplierOrderDAO = (SupplierOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.SUPPLIERORDER);
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.SUPPLIER);
    GemDAO gemDAO = (GemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.GEM);
    SupplierOrderDetailsDAO supplierOrderDetailsDAO = (SupplierOrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.SUPPLIERORDERDETAILS);
    SupplierPaymentDAO supplierPaymentDAO = (SupplierPaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.SUPPLIERPAYMENT);

    @Override
    public String getNextSupplierOrderId() throws SQLException {
        return supplierOrderDAO.getNext();
    }

    @Override
    public String getNextSupplierPaymentId() throws SQLException {
        return supplierPaymentDAO.getNext();
    }

    @Override
    public boolean saveSupplierOrder(SupplierOrder supplierOrder) throws SQLException {

        System.out.println("clicked");
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false); // Start transaction

        try {
            //save payment
//            boolean isPaymentSaved = SQLUtil.execute(
//                    "INSERT INTO supplierpayment (supplier_payment_id, method, total_amount) VALUES (?, ?, ?)",
//                    supplierOrder.getPaymentId(),
//                    supplierOrder.getMethod(),
//                    supplierOrder.getTotalAmount()
//            );

            boolean isSaveSupplierPayment = supplierPaymentDAO.save(supplierOrder);
            if (!isSaveSupplierPayment) {
                connection.rollback();
                return false;
            }
            // Save the order
//            boolean isOrderSaved = SQLUtil.execute(
//                    "INSERT INTO supplierorder (supplier_order_id, supplier_id, order_date, supplier_payment_id) VALUES (?, ?, ?, ?)",
//                    supplierOrder.getSupplierOrderId(),
//                    supplierOrder.getSupplierId(),
//                    supplierOrder.getOrderDate(),
//                    supplierOrder.getPaymentId()
//            );

            boolean isSaveSupplierOrder = supplierOrderDAO.save(supplierOrder);

            if (!isSaveSupplierOrder) {
                connection.rollback();
                return false;
            }

            // Save order details
            boolean isSupplierOrderDetailListSaved = supplierOrderDetailsDAO.saveSupplierOrderDetailsList(supplierOrder.getSupplierOrderDetailsDTOS());
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

    @Override
    public ArrayList<String> getAllSupplierIds() throws SQLException {
        return supplierDAO.getAllSupplierIds();
    }

    @Override
    public Supplier findByIdSupplier(String selectedItemId) throws SQLException {
        return supplierDAO.findById(selectedItemId);
    }

    @Override
    public ArrayList<String> getAllGemIds() throws SQLException {
        return gemDAO.getAllGemIds();
    }

    @Override
    public Gem findByIdGem(String selectedItemId) throws SQLException {
        return gemDAO.findById(selectedItemId);
    }
}
