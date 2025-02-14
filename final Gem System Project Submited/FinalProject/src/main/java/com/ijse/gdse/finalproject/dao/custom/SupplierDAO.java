package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Supplier;

public interface SupplierDAO extends CrudDAO<SupplierDTO> {
    public ArrayList<String> getAllSupplierIds() throws SQLException;
    public SupplierDTO findById(String selectedSupplierId) throws SQLException;
    public int getSupplierCount() throws SQLException;
}
