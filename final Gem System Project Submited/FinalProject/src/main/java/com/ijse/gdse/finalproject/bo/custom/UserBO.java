package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {

    public String getNextUserId() throws SQLException;
    public boolean saveUser(UserDTO userDTO) throws SQLException;
    public UserDTO getUserDetails(String username) throws SQLException;
    public ArrayList<String> getAllUserIds() throws SQLException;
}
