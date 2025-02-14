package com.ijse.gdse.finalproject.dao.custom;

import com.ijse.gdse.finalproject.dao.CrudDAO;
import com.ijse.gdse.finalproject.entity.SupplierOrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierOrderDetailsDAO extends CrudDAO<SupplierOrderDetails> {

    public boolean saveSupplierOrderDetailsList(ArrayList<SupplierOrderDetails> supplierOrderDetailsDTOS) throws SQLException;
}
