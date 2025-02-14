package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.UserBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.UserDAO;
import com.ijse.gdse.finalproject.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.USER);

    @Override
    public String getNextUserId() throws SQLException {
        return userDAO.getNext();
    }


    @Override
    public boolean saveUser(UserDTO userDTO) throws SQLException {
        return userDAO.save(userDTO);
    }

    @Override
    public UserDTO getUserDetails(String username) throws SQLException {
        return userDAO.getUserDetails(username);
    }

    @Override
    public ArrayList<String> getAllUserIds() throws SQLException {
        return userDAO.getAllUserIds();
    }
}
