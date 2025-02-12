package com.ijse.gdse.finalproject.model;

import com.ijse.gdse.finalproject.dto.OrderDetailsDTO;
import com.ijse.gdse.finalproject.dto.SupplierOrderDetailsDTO;
import com.ijse.gdse.finalproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderDetailsModel {
    private final GemModel gemModel = new GemModel();

    public boolean saveSupplierOrderDetailsList(ArrayList<SupplierOrderDetailsDTO> supplierOrderDetailsDTOS) throws SQLException {
        for (SupplierOrderDetailsDTO supplierOrderDetailsDTO : supplierOrderDetailsDTOS) {
            // @isOrderDetailsSaved: Saves the individual order detail
            boolean isSupplierOrderDetailsSaved = saveSupplierOrderDetail(supplierOrderDetailsDTO);
            if (!isSupplierOrderDetailsSaved) {
                // Return false if saving any order detail fails
                return false;
            }

            boolean isGemUpdated = gemModel.reduceeQty(supplierOrderDetailsDTO);
            if (!isGemUpdated) {
                // Return false if updating the item quantity fails
                return false;
            }

        }
        return true;
    }

    private boolean saveSupplierOrderDetail(SupplierOrderDetailsDTO supplierOrderDetailsDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into supplierorderdetails values (?,?,?,?)",
                supplierOrderDetailsDTO.getSupplierOrderId(),
                supplierOrderDetailsDTO.getGemId(),
                supplierOrderDetailsDTO.getQuantity(),
                supplierOrderDetailsDTO.getPrice()
        );
    }
}
