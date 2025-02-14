package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.GemBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.GemDAO;
import com.ijse.gdse.finalproject.dto.Gem2DTO;
import com.ijse.gdse.finalproject.dto.GemDTO;
import com.ijse.gdse.finalproject.dto.OrderDetailsDTO;
import com.ijse.gdse.finalproject.dto.SupplierOrderDetailsDTO;
import com.ijse.gdse.finalproject.entity.Gem;
import com.ijse.gdse.finalproject.entity.Gem2;
import com.ijse.gdse.finalproject.entity.OrderDetails;
import com.ijse.gdse.finalproject.entity.SupplierOrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class GemBOImpl implements GemBO {

    GemDAO gemDAO = (GemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.GEM);


    @Override
    public ArrayList<Gem2> getAllCategory() throws SQLException {
        return gemDAO.getAllCategory();
    }

    @Override
    public String getNextGemId() throws SQLException {
        return gemDAO.getNext();
    }

    @Override
    public boolean saveGem(Gem gem) throws SQLException {
        return gemDAO.save(gem);
    }

    @Override
    public ArrayList<Gem> getAllGem() throws SQLException {
        return gemDAO.getAll();
    }

    @Override
    public boolean deleteGem(String gemId) throws SQLException {
        return gemDAO.delete(gemId);
    }

    @Override
    public boolean updateGem(Gem gem) throws SQLException {
        return gemDAO.update(gem);
    }

    @Override
    public ArrayList<String> getAllGemIds() throws SQLException {
        return gemDAO.getAllGemIds();
    }

    @Override
    public Gem findById(String selectedItemId) throws SQLException {
        return gemDAO.findById(selectedItemId);
    }

    @Override
    public boolean reduceQty(OrderDetails orderDetails) throws SQLException {
        return gemDAO.reduceQty(orderDetails);
    }

    @Override
    public boolean reduceeQty(SupplierOrderDetails supplierOrderDetails) throws SQLException {
        return gemDAO.reduceeQty(supplierOrderDetails);
    }
}
