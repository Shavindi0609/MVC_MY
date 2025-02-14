package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.entity.Gem;
import com.ijse.gdse.finalproject.entity.Gem2;
import com.ijse.gdse.finalproject.entity.OrderDetails;
import com.ijse.gdse.finalproject.entity.SupplierOrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GemDAO extends CrudDAO<Gem> {

    ArrayList<Gem2> getAllCategory() throws SQLException;
    public ArrayList<String> getAllGemIds() throws SQLException;
    public Gem findById(String selectedItemId) throws SQLException;
    public boolean reduceQty(OrderDetails orderDetails) throws SQLException;
    public boolean reduceeQty(SupplierOrderDetails supplierOrderDetails) throws SQLException;
}
