package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.GemDAO;
import com.ijse.gdse.finalproject.dao.custom.SupplierOrderDetailsDAO;
import com.ijse.gdse.finalproject.dto.SupplierOrderDetailsDTO;
import com.ijse.gdse.finalproject.dao.SQLUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderDetailsDAOImpl implements SupplierOrderDetailsDAO {

    GemDAO gemDAO = new GemDAOImpl();

    public boolean saveSupplierOrderDetailsList(ArrayList<SupplierOrderDetailsDTO> supplierOrderDetailsDTOS) throws SQLException {
        for (SupplierOrderDetailsDTO supplierOrderDetailsDTO : supplierOrderDetailsDTOS) {
            // @isOrderDetailsSaved: Saves the individual order detail
            boolean isSupplierOrderDetailsSaved = save(supplierOrderDetailsDTO);
            if (!isSupplierOrderDetailsSaved) {
                // Return false if saving any order detail fails
                return false;
            }

            boolean isGemUpdated = gemDAO.reduceeQty(supplierOrderDetailsDTO);
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
    public ArrayList<SupplierOrderDetailsDTO> getAll() throws SQLException {
        return null;
    }

    public boolean save(SupplierOrderDetailsDTO supplierOrderDetailsDTO) throws SQLException {
        return SQLUtil.execute(
                "insert into supplierorderdetails values (?,?,?,?)",
                supplierOrderDetailsDTO.getSupplierOrderId(),
                supplierOrderDetailsDTO.getGemId(),
                supplierOrderDetailsDTO.getQuantity(),
                supplierOrderDetailsDTO.getPrice()
        );
    }

    @Override
    public boolean update(SupplierOrderDetailsDTO DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }
}
