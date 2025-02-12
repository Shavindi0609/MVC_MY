package com.ijse.gdse.finalproject.model;

import com.ijse.gdse.finalproject.dto.SupplierDTO;
import com.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier");

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



    public String getNextSupplierId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select supplier_id from supplier order by supplier_id desc limit 1");

        if(rst.next()){
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIndex = i+1;
            return  String.format("S%03d", newIndex);
        }
        return "S001";
    }

    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into supplier(supplier_id,name,nic,email,phone,address) values (?,?,?,?,?,?)",
                supplierDTO.getSupplierId(),
                supplierDTO.getName(),
                supplierDTO.getNic(),
                supplierDTO.getEmail(),
                supplierDTO.getPhone(),
                supplierDTO.getAddress()
        );
    }

    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException {
        return CrudUtil.execute(
                "update supplier set name=?, nic=?, email=?, phone=?, address=? where supplier_id=?",
                supplierDTO.getName(),
                supplierDTO.getNic(),
                supplierDTO.getEmail(),
                supplierDTO.getPhone(),
                supplierDTO.getAddress(),
                supplierDTO.getSupplierId()
        );
    }

    public boolean deleteSuppler(String supplierId) throws SQLException {
        return CrudUtil.execute("delete from supplier where supplier_id = ?" , supplierId);
    }

    public ArrayList<String> getAllSupplierIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select supplier_id from supplier");

        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }

        return supplierIds;
    }

    public SupplierDTO findById(String selectedSupplierId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier where supplier_id=?", selectedSupplierId);

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
        ResultSet rst = CrudUtil.execute(query);
        if (rst.next()) {
            return rst.getInt(1);
        }
        return 0;
    }
}

