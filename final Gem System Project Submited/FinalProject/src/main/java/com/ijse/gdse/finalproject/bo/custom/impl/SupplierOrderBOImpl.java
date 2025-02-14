package com.ijse.gdse.finalproject.bo.custom.impl;

import com.ijse.gdse.finalproject.bo.custom.SupplierOrderBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.GemDAO;
import com.ijse.gdse.finalproject.dao.custom.SupplierDAO;
import com.ijse.gdse.finalproject.dao.custom.SupplierOrderDAO;
import com.ijse.gdse.finalproject.dto.CustomerDTO;
import com.ijse.gdse.finalproject.dto.GemDTO;
import com.ijse.gdse.finalproject.dto.SupplierDTO;
import com.ijse.gdse.finalproject.dto.SupplierOrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderBOImpl implements SupplierOrderBO {

    SupplierOrderDAO supplierOrderDAO = (SupplierOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.SUPPLIERORDER);
    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.SUPPLIER);
    GemDAO gemDAO = (GemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.GEM);

    @Override
    public String getNextSupplierOrderId() throws SQLException {
        return supplierOrderDAO.getNext();
    }

    @Override
    public String getNextSupplierPaymentId() throws SQLException {
        return supplierOrderDAO.getNextSupplierPaymentId();
    }

    @Override
    public boolean saveSupplierOrder(SupplierOrderDTO supplierOrderDTO) throws SQLException {
        return supplierOrderDAO.save(supplierOrderDTO);
    }

    @Override
    public ArrayList<String> getAllSupplierIds() throws SQLException {
        return supplierDAO.getAllSupplierIds();
    }

    @Override
    public SupplierDTO findByIdSupplier(String selectedItemId) throws SQLException {
        return supplierDAO.findById(selectedItemId);
    }

    @Override
    public ArrayList<String> getAllGemIds() throws SQLException {
        return gemDAO.getAllGemIds();
    }

    @Override
    public GemDTO findByIdGem(String selectedItemId) throws SQLException {
        return gemDAO.findById(selectedItemId);
    }
}
