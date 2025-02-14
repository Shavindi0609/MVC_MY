package com.ijse.gdse.finalproject.bo.custom;

import com.ijse.gdse.finalproject.bo.SuperBO;
import com.ijse.gdse.finalproject.entity.Login;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginBO extends SuperBO {
    public ArrayList<Login> getAllIdAndPassword() throws SQLException;
}
