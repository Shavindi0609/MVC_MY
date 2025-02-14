package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.entity.Gem;
import com.ijse.gdse.finalproject.entity.Supplier;
import com.ijse.gdse.finalproject.entity.SupplierOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierOrderBO extends SuperBO {

    String getNextSupplierOrderId() throws SQLException;
    String getNextSupplierPaymentId() throws SQLException;
    public boolean saveSupplierOrder(SupplierOrder supplierOrder) throws SQLException;

    ArrayList<String> getAllSupplierIds() throws SQLException;
    public Supplier findByIdSupplier(String selectedItemId) throws SQLException;

    ArrayList<String> getAllGemIds() throws SQLException;
    public Gem findByIdGem(String selectedItemId) throws SQLException;
}
