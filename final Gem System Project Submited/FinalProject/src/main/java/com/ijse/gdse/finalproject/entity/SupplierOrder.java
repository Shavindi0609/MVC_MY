package com.ijse.gdse.finalproject.entity;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupplierOrder {

    private String supplierOrderId;
    private String supplierId;
    private Date orderDate;

    private String paymentId;
    private double totalAmount;
    private String method;


    // @orderDetailsDTOS: A list of OrderDetailsDTO objects, each representing an item in the order
    private ArrayList<SupplierOrderDetails> supplierOrderDetailsDTOS;

}
