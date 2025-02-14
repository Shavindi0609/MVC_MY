package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.CategoryBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.CategoryDAO;
import com.ijse.gdse.finalproject.entity.Category;


import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryBOImpl implements CategoryBO {

    CategoryDAO categoryDAO = (CategoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CATEGORY);

    @Override
    public String getNextCategoryId() throws SQLException {
        return categoryDAO.getNext();
    }

    @Override
    public ArrayList<Category> getAllCategory() throws SQLException {
        return categoryDAO.getAll();
    }

    @Override
    public boolean saveCategory(Category category) throws SQLException {
        return categoryDAO.save(category);
    }

    @Override
    public boolean deleteCategory(String categoryId) throws SQLException {
        return categoryDAO.delete(categoryId);
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        return categoryDAO.update(category);
    }
}
