package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.GemBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.GemDAO;
import com.ijse.gdse.finalproject.dto.Gem2DTO;
import com.ijse.gdse.finalproject.dto.GemDTO;
import com.ijse.gdse.finalproject.dto.OrderDetailsDTO;
import com.ijse.gdse.finalproject.dto.SupplierOrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class GemBOImpl implements GemBO {

    GemDAO gemDAO = (GemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.GEM);


    @Override
    public ArrayList<Gem2DTO> getAllCategory() throws SQLException {
        return gemDAO.getAllCategory();
    }

    @Override
    public String getNextGemId() throws SQLException {
        return gemDAO.getNext();
    }

    @Override
    public boolean saveGem(GemDTO gemDTO) throws SQLException {
        return gemDAO.save(gemDTO);
    }

    @Override
    public ArrayList<GemDTO> getAllGem() throws SQLException {
        return gemDAO.getAll();
    }

    @Override
    public boolean deleteGem(String gemId) throws SQLException {
        return gemDAO.delete(gemId);
    }

    @Override
    public boolean updateGem(GemDTO gemDTO) throws SQLException {
        return gemDAO.update(gemDTO);
    }

    @Override
    public ArrayList<String> getAllGemIds() throws SQLException {
        return gemDAO.getAllGemIds();
    }

    @Override
    public GemDTO findById(String selectedItemId) throws SQLException {
        return gemDAO.findById(selectedItemId);
    }

    @Override
    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return gemDAO.reduceQty(orderDetailsDTO);
    }

    @Override
    public boolean reduceeQty(SupplierOrderDetailsDTO supplierOrderDetailsDTO) throws SQLException {
        return gemDAO.reduceeQty(supplierOrderDetailsDTO);
    }
}
