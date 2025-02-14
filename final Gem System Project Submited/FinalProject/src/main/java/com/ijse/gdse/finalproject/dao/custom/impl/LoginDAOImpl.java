package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.LoginDAO;
import com.ijse.gdse.finalproject.dto.LoginDTO;
import com.ijse.gdse.finalproject.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDAOImpl implements LoginDAO {
    public ArrayList<LoginDTO> getAllIdAndPassword() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT user_name,password FROM user");

        ArrayList<LoginDTO> loginDTOS = new ArrayList<>();

        while (rst.next()) {
            LoginDTO loginDTO = new LoginDTO(
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
    public ArrayList<LoginDTO> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(LoginDTO DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean update(LoginDTO DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }
}
