package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.dto.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierOrderBO extends SuperBO {

    String getNextSupplierOrderId() throws SQLException;
    String getNextSupplierPaymentId() throws SQLException;
    public boolean saveSupplierOrder(SupplierOrderDTO supplierOrderDTO) throws SQLException;

    ArrayList<String> getAllSupplierIds() throws SQLException;
    public SupplierDTO findByIdSupplier(String selectedItemId) throws SQLException;

    ArrayList<String> getAllGemIds() throws SQLException;
    public GemDTO findByIdGem(String selectedItemId) throws SQLException;
}
