package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.GemDAO;
import com.ijse.gdse.finalproject.dao.custom.SupplierOrderDetailsDAO;
import com.ijse.gdse.finalproject.dao.SQLUtil;
import com.ijse.gdse.finalproject.entity.SupplierOrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderDetailsDAOImpl implements SupplierOrderDetailsDAO {

    GemDAO gemDAO = new GemDAOImpl();

    public boolean saveSupplierOrderDetailsList(ArrayList<SupplierOrderDetails> supplierOrderDetailsDTOS) throws SQLException {
        for (SupplierOrderDetails supplierOrderDetails : supplierOrderDetailsDTOS) {
            // @isOrderDetailsSaved: Saves the individual order detail
            boolean isSupplierOrderDetailsSaved = save(supplierOrderDetails);
            if (!isSupplierOrderDetailsSaved) {
                // Return false if saving any order detail fails
                return false;
            }

            boolean isGemUpdated = gemDAO.reduceeQty(supplierOrderDetails);
            if (!isGemUpdated) {
                // Return false if updating the item quantity fails
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
    public ArrayList<SupplierOrderDetails> getAll() throws SQLException {
        return null;
    }

    public boolean save(SupplierOrderDetails supplierOrderDetails) throws SQLException {
        return SQLUtil.execute(
                "insert into supplierorderdetails values (?,?,?,?)",
                supplierOrderDetails.getSupplierOrderId(),
                supplierOrderDetails.getGemId(),
                supplierOrderDetails.getQuantity(),
                supplierOrderDetails.getPrice()
        );
    }

    @Override
    public boolean update(SupplierOrderDetails DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }
}
