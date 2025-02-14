package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.entity.Login;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoginDAO extends CrudDAO<Login> {

    public ArrayList<Login> getAllIdAndPassword() throws SQLException;
}
