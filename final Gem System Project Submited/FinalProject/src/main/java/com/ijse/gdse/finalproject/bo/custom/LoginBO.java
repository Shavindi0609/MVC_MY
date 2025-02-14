package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.dto.LoginDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginBO extends SuperBO {
    public ArrayList<LoginDTO> getAllIdAndPassword() throws SQLException;
}
