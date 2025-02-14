package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {

    public ArrayList<Supplier> getAllSupplier() throws SQLException;
    public String getNextSupplierId() throws SQLException;
    public boolean saveSupplier(Supplier supplier) throws SQLException;
    public boolean updateSupplier(Supplier supplier) throws SQLException;
    public boolean deleteSupplier(String supplierId) throws SQLException;
    public ArrayList<String> getAllSupplierIds() throws SQLException;
    public Supplier findById(String selectedSupplierId) throws SQLException;
    public int getSupplierCount() throws SQLException;
}
