package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.SupplierBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.SupplierDAO;
import com.ijse.gdse.finalproject.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public ArrayList<Supplier> getAllSupplier() throws SQLException {
        return supplierDAO.getAll();
    }

    @Override
    public String getNextSupplierId() throws SQLException {
        return supplierDAO.getNext();
    }

    @Override
    public boolean saveSupplier(Supplier supplier) throws SQLException {
        return supplierDAO.save(supplier);
    }

    @Override
    public boolean updateSupplier(Supplier supplier) throws SQLException {
        return supplierDAO.update(supplier);
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
    public Supplier findById(String selectedSupplierId) throws SQLException {
        return supplierDAO.findById(selectedSupplierId);
    }

    @Override
    public int getSupplierCount() throws SQLException {
        return supplierDAO.getSupplierCount();
    }
}
