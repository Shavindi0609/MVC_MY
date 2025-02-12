package com.ijse.gdse.finalproject.model;

import com.ijse.gdse.finalproject.dto.LoginDTO;
import com.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginModel {
    public ArrayList<LoginDTO> getAllIdAndPassword() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT user_name,password FROM user");

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
}
