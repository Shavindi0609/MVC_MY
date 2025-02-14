package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.SupplierBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.SupplierDAO;
import com.ijse.gdse.finalproject.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException {
        return supplierDAO.getAll();
    }

    @Override
    public String getNextSupplierId() throws SQLException {
        return supplierDAO.getNext();
    }

    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException {
        return supplierDAO.save(supplierDTO);
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException {
        return supplierDAO.update(supplierDTO);
    }

    @Override
    public boolean deleteSupplier(String supplierId) throws SQLException {
        return supplierDAO.delete(supplierId);
    }

    @Override
    public ArrayList<String> getAllSupplierIds() throws SQLException {
        return null;
    }

    @Override
    public SupplierDTO findById(String selectedSupplierId) throws SQLException {
        return supplierDAO.findById(selectedSupplierId);
    }

    @Override
    public int getSupplierCount() throws SQLException {
        return supplierDAO.getSupplierCount();
    }
}
