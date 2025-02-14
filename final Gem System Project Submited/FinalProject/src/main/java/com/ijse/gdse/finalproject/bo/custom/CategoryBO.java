package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.entity.Category;


import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryBO extends SuperBO {

     String getNextCategoryId() throws SQLException;
     ArrayList<Category> getAllCategory() throws SQLException;
     boolean saveCategory(Category category) throws SQLException;
     boolean deleteCategory(String categoryId) throws SQLException;
     boolean updateCategory(Category category) throws SQLException;
}
