package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.LoginDAO;
import com.ijse.gdse.finalproject.dao.SQLUtil;
import com.ijse.gdse.finalproject.entity.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDAOImpl implements LoginDAO {
    public ArrayList<Login> getAllIdAndPassword() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT user_name,password FROM user");

        ArrayList<Login> loginDTOS = new ArrayList<>();

        while (rst.next()) {
            Login loginDTO = new Login(
                    rst.getString(1),
                    rst.getString(2)
            );


            loginDTOS.add(loginDTO);
        }
        return loginDTOS;

    }

    @Override
    public String getNext() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<Login> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(Login DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Login DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }
}
