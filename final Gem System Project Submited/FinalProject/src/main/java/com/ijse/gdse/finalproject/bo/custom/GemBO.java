package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.entity.Gem;
import com.ijse.gdse.finalproject.entity.Gem2;
import com.ijse.gdse.finalproject.entity.OrderDetails;
import com.ijse.gdse.finalproject.entity.SupplierOrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GemBO extends SuperBO {

    public ArrayList<Gem2> getAllCategory() throws SQLException;
    public String getNextGemId() throws SQLException;
    public boolean saveGem(Gem gem) throws SQLException;
    public ArrayList<Gem> getAllGem() throws SQLException;
    public boolean deleteGem(String gemId) throws SQLException;
    public boolean updateGem(Gem gem) throws SQLException;
    public ArrayList<String> getAllGemIds() throws SQLException;
    public Gem findById(String selectedItemId) throws SQLException;
    public boolean reduceQty(OrderDetails orderDetails) throws SQLException;
    public boolean reduceeQty(SupplierOrderDetails supplierOrderDetails) throws SQLException;
}
