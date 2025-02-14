package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.dto.SupplierOrderDTO;

import java.sql.SQLException;

public interface SupplierOrderDAO extends CrudDAO<SupplierOrderDTO> {

    public String getNextSupplierPaymentId() throws SQLException;
}
