package com.ijse.gdse.finalproject.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {

    String getNext () throws SQLException;
    ArrayList<T> getAll() throws SQLException;
    boolean save(T DTO) throws SQLException;
    boolean update(T DTO) throws SQLException;
    boolean delete(String Id) throws SQLException;
}
