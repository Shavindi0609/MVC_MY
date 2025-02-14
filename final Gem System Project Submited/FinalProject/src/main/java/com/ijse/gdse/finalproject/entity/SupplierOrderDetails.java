package com.ijse.gdse.finalproject.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupplierOrderDetails {
    private String supplierOrderId;
    private String gemId;
    private int quantity;
    private double price;

}
