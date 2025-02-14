package com.ijse.gdse.finalproject.dao.custom.impl;

import com.ijse.gdse.finalproject.dao.custom.SupplierDAO;
import com.ijse.gdse.finalproject.dto.SupplierDTO;
import com.ijse.gdse.finalproject.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {

    public ArrayList<SupplierDTO> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from supplier");

        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();

        while (rst.next()) {
            SupplierDTO supplierDTO = new SupplierDTO(
                    rst.getString(1),
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5),   //phone
                    rst.getString(6)    //address
            );
            supplierDTOS.add(supplierDTO);
        }
        return supplierDTOS;
    }

    public String getNext() throws SQLException {
        ResultSet rst = SQLUtil.execute("select supplier_id from supplier order by supplier_id desc limit 1");

        if(rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIndex = i+1;
            return  String.format("S%03d", newIndex);
        }
        return "S001";
    }

    public boolean save(SupplierDTO supplierDTO) throws SQLException {
        return SQLUtil.execute(
                "insert into supplier(supplier_id,name,nic,email,phone,address) values (?,?,?,?,?,?)",
                supplierDTO.getSupplierId(),
                supplierDTO.getName(),
                supplierDTO.getNic(),
                supplierDTO.getEmail(),
                supplierDTO.getPhone(),
                supplierDTO.getAddress()
        );
    }

    public boolean update(SupplierDTO supplierDTO) throws SQLException {
        return SQLUtil.execute(
                "update supplier set name=?, nic=?, email=?, phone=?, address=? where supplier_id=?",
                supplierDTO.getName(),
                supplierDTO.getNic(),
                supplierDTO.getEmail(),
                supplierDTO.getPhone(),
                supplierDTO.getAddress(),
                supplierDTO.getSupplierId()
        );
    }

    public boolean delete(String supplierId) throws SQLException {
        return SQLUtil.execute("delete from supplier where supplier_id = ?" , supplierId);
    }

    public ArrayList<String> getAllSupplierIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select supplier_id from supplier");

        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }

        return supplierIds;
    }

    public SupplierDTO findById(String selectedSupplierId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from supplier where supplier_id=?", selectedSupplierId);

        if (rst.next()) {
            return new SupplierDTO(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5),
                     rst.getString(6)// Phone
            );
        }
        return null;
    }

    public int getSupplierCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM supplier";
        ResultSet rst = SQLUtil.execute(query);
        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }
}

