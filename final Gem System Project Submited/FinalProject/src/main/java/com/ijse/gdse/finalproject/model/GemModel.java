package com.ijse.gdse.finalproject.model;

import com.ijse.gdse.finalproject.dto.Gem2DTO;
import com.ijse.gdse.finalproject.dto.GemDTO;
import com.ijse.gdse.finalproject.dto.OrderDetailsDTO;
import com.ijse.gdse.finalproject.dto.SupplierOrderDetailsDTO;
import com.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GemModel {
    public static ArrayList<Gem2DTO> getAllCategory() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from category");

        ArrayList<Gem2DTO> Gem2DTOS = new ArrayList<>();

        while (rst.next()) {
            Gem2DTO Gem2DTO = new Gem2DTO(
                    rst.getString(1),
                    rst.getString(2)

            );
            Gem2DTOS.add(Gem2DTO);
        }
        return Gem2DTOS;
    }

    public String getNextGemId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select gem_id from gem order by gem_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("G%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "G001"; // Return the default customer ID if no data is found
    }

    public boolean saveGem(GemDTO gemDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO gem (gem_id, gem_name, gem_color, size, price, qty, is_certified,category_id) VALUES (?,?,?,?,?,?,?,?)",
                gemDTO.getGem_id(),
                gemDTO.getGem_name(),
                gemDTO.getGem_color(),
                gemDTO.getSize(),
                gemDTO.getPrice(),
                gemDTO.getQty(),
                gemDTO.getIs_cetified(),
                gemDTO.getCategory_id()
        );
    }


    public ArrayList<GemDTO> getAllGems() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from gem");

        ArrayList<GemDTO> gemDTOS = new ArrayList<>();

        while (rst.next()) {
            GemDTO gemDTO = new GemDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getInt(6),
                    rst.getBoolean(7),
                    rst.getString(8)
            );
            gemDTOS.add(gemDTO);
        }
        return gemDTOS;
    }

    public boolean deleteGem(String gemId) throws SQLException {
        return CrudUtil.execute("delete from gem where gem_id=?", gemId);
    }

    public boolean updateGem(GemDTO gemDTO) throws SQLException {
        return CrudUtil.execute(
                "update gem set gem_name=?, gem_color=?, size=?, price=?, qty=? , is_certified=? , category_id=? where gem_id=?",
                gemDTO.getGem_name(),
                gemDTO.getGem_color(),
                gemDTO.getSize(),
                gemDTO.getPrice(),
                gemDTO.getQty(),
                gemDTO.getIs_cetified(),
                gemDTO.getCategory_id(),
                gemDTO.getGem_id()
        );
    }

    public ArrayList<String> getAllGemIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select gem_id from gem");
        ArrayList<String> gemIds = new ArrayList<>();
        while (rst.next()) {
            gemIds.add(rst.getString(1));
        }
        return gemIds;
    }

    public GemDTO findById(String selectedItemId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from gem where gem_id=?", selectedItemId);
        if (rst.next()) {
            return new GemDTO(
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

    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return CrudUtil.execute(
                "update gem set qty = qty - ? where gem_id = ?",
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getGemId()
        );
    }

    public boolean reduceeQty(SupplierOrderDetailsDTO supplierOrderDetailsDTO) throws SQLException {
        return CrudUtil.execute(
                "update gem set qty = qty + ? where gem_id = ?",
                supplierOrderDetailsDTO.getQuantity(),
                supplierOrderDetailsDTO.getGemId()
        );
    }
}
