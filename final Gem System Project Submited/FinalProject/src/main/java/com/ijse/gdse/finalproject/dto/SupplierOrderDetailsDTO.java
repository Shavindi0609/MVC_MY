package com.ijse.gdse.finalproject.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupplierOrderDetailsDTO {
    private String supplierOrderId;
    private String gemId;
    private int quantity;
    private double price;

}
