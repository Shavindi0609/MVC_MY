package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;


public interface SupplierDAO extends CrudDAO<Supplier> {
    public ArrayList<String> getAllSupplierIds() throws SQLException;
    public Supplier findById(String selectedSupplierId) throws SQLException;
    public int getSupplierCount() throws SQLException;
}
