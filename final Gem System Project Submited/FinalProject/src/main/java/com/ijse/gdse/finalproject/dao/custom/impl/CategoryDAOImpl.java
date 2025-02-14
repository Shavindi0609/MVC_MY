package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.CategoryDAO;
import com.ijse.gdse.finalproject.dto.CategoryDTO;
import com.ijse.gdse.finalproject.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CategoryDAOImpl implements CategoryDAO {

    public String getNext() throws SQLException {
        ResultSet rst = SQLUtil.execute("select category_id from category order by category_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("c%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "c001";

    }

    public ArrayList<CategoryDTO> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from category");

        ArrayList<CategoryDTO> categoryDTOS = new ArrayList<>();

        while (rst.next()) {
            CategoryDTO categoryDTO = new CategoryDTO(
                    rst.getString(1),  // Customer ID
                    rst.getString(2)  // Name
            );
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }

    public boolean save(CategoryDTO categoryDTO) throws SQLException {
        return SQLUtil.execute(
                "insert into category(category_id , category_name) values (?,?)",
                categoryDTO.getCategoryId(),
                categoryDTO.getCategoryName()

        );
    }

    public boolean delete(String categoryId) throws SQLException {
        return SQLUtil.execute("delete from category where category_id=?", categoryId);
    }

    public boolean update(CategoryDTO categoryDTO) throws SQLException {
        return SQLUtil.execute(
                "update category set  category_name=? where category_id=?",
                categoryDTO.getCategoryName(),
                categoryDTO.getCategoryId()
        );
    }
}
