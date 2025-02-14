package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.dto.LoginDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginDAO extends CrudDAO<LoginDTO> {

    public ArrayList<LoginDTO> getAllIdAndPassword() throws SQLException;
}
