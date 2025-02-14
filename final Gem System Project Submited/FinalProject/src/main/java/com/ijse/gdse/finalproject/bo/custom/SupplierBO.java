package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {

    public ArrayList<SupplierDTO> getAllSupplier() throws SQLException;
    public String getNextSupplierId() throws SQLException;
    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException;
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException;
    public boolean deleteSupplier(String supplierId) throws SQLException;
    public ArrayList<String> getAllSupplierIds() throws SQLException;
    public SupplierDTO findById(String selectedSupplierId) throws SQLException;
    public int getSupplierCount() throws SQLException;
}
