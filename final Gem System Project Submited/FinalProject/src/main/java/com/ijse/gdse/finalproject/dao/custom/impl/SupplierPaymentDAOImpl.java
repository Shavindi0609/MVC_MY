package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.SQLUtil;
import com.ijse.gdse.finalproject.dao.custom.SupplierPaymentDAO;
import com.ijse.gdse.finalproject.entity.SupplierOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierPaymentDAOImpl implements SupplierPaymentDAO {
    @Override
    public String getNext() throws SQLException {
        ResultSet rst = SQLUtil.execute("select supplier_payment_id from supplierpayment order by supplier_payment_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("Y%03d", newIdIndex);
        }

        return "Y001";
    }

    @Override
    public ArrayList<SupplierOrder> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(SupplierOrder DTO) throws SQLException {
       return SQLUtil.execute(
                "INSERT INTO supplierpayment (supplier_payment_id, method, total_amount) VALUES (?, ?, ?)",
                DTO.getPaymentId(),
                DTO.getMethod(),
                DTO.getTotalAmount()
       );
    }

    @Override
    public boolean update(SupplierOrder DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }
}
