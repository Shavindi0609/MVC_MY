package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.dto.SupplierOrderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierOrderDetailsDAO extends CrudDAO<SupplierOrderDetailsDTO> {

    public boolean saveSupplierOrderDetailsList(ArrayList<SupplierOrderDetailsDTO> supplierOrderDetailsDTOS) throws SQLException;
}
