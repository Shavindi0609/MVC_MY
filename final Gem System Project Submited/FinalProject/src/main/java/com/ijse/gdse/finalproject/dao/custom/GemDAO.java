package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.dto.Gem2DTO;
import com.ijse.gdse.finalproject.dto.GemDTO;
import com.ijse.gdse.finalproject.dto.OrderDetailsDTO;
import com.ijse.gdse.finalproject.dto.SupplierOrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GemDAO extends CrudDAO<GemDTO> {

    ArrayList<Gem2DTO> getAllCategory() throws SQLException;
    public ArrayList<String> getAllGemIds() throws SQLException;
    public GemDTO findById(String selectedItemId) throws SQLException;
    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException;
    public boolean reduceeQty(SupplierOrderDetailsDTO supplierOrderDetailsDTO) throws SQLException;
}
