package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO extends CrudDAO<User> {

    public User getUserDetails(String username) throws SQLException;
    public ArrayList<String> getAllUserIds() throws SQLException;
}
