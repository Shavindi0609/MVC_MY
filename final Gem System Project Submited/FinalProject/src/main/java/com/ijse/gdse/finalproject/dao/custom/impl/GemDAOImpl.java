package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.GemDAO;
import com.ijse.gdse.finalproject.dao.SQLUtil;
import com.ijse.gdse.finalproject.entity.Gem;
import com.ijse.gdse.finalproject.entity.Gem2;
import com.ijse.gdse.finalproject.entity.OrderDetails;
import com.ijse.gdse.finalproject.entity.SupplierOrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GemDAOImpl implements GemDAO {

    public ArrayList<Gem2> getAllCategory() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from category");

        ArrayList<Gem2> Gem2DTOS = new ArrayList<>();

        while (rst.next()) {
            Gem2 Gem2 = new Gem2(
                    rst.getString(1),
                    rst.getString(2)

            );
            Gem2DTOS.add(Gem2);
        }
        return Gem2DTOS;
    }

    public String getNext() throws SQLException {
        ResultSet rst = SQLUtil.execute("select gem_id from gem order by gem_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("G%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "G001"; // Return the default customer ID if no data is found
    }

    public boolean save(Gem gem) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO gem (gem_id, gem_name, gem_color, size, price, qty, is_certified,category_id) VALUES (?,?,?,?,?,?,?,?)",
                gem.getGem_id(),
                gem.getGem_name(),
                gem.getGem_color(),
                gem.getSize(),
                gem.getPrice(),
                gem.getQty(),
                gem.getIs_cetified(),
                gem.getCategory_id()
        );
    }

    public ArrayList<Gem> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from gem");

        ArrayList<Gem> gemDTOS = new ArrayList<>();

        while (rst.next()) {
            Gem gem = new Gem(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getBoolean(7),
                    rst.getString(8)
            );
            gemDTOS.add(gem);
        }
        return gemDTOS;
    }

    public boolean delete(String gemId) throws SQLException {
        return SQLUtil.execute("delete from gem where gem_id=?", gemId);
    }

    public boolean update(Gem gem) throws SQLException {
        return SQLUtil.execute(
                "update gem set gem_name=?, gem_color=?, size=?, price=?, qty=? , is_certified=? , category_id=? where gem_id=?",
                gem.getGem_name(),
                gem.getGem_color(),
                gem.getSize(),
                gem.getPrice(),
                gem.getQty(),
                gem.getIs_cetified(),
                gem.getCategory_id(),
                gem.getGem_id()
        );
    }

    public ArrayList<String> getAllGemIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select gem_id from gem");
        ArrayList<String> gemIds = new ArrayList<>();
        while (rst.next()) {
            gemIds.add(rst.getString(1));
        }
        return gemIds;
    }

    public Gem findById(String selectedItemId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from gem where gem_id=?", selectedItemId);
        if (rst.next()) {
            return new Gem(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getBoolean(7),
                    rst.getString(8)
            );
        }
        return null;
    }

    public boolean reduceQty(OrderDetails orderDetails) throws SQLException {
        return SQLUtil.execute(
                "update gem set qty = qty - ? where gem_id = ?",
                orderDetails.getQuantity(),
                orderDetails.getGemId()
        );
    }

    public boolean reduceeQty(SupplierOrderDetails supplierOrderDetails) throws SQLException {
        return SQLUtil.execute(
                "update gem set qty = qty + ? where gem_id = ?",
                supplierOrderDetails.getQuantity(),
                supplierOrderDetails.getGemId()
        );
    }
}
