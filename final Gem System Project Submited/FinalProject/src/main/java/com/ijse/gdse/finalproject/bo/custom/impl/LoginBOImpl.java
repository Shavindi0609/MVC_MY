package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.LoginBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.LoginDAO;
import com.ijse.gdse.finalproject.dto.LoginDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginBOImpl implements LoginBO {

    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.LOGIN);

    @Override
    public ArrayList<LoginDTO> getAllIdAndPassword() throws SQLException {
        return loginDAO.getAllIdAndPassword();
    }
}
