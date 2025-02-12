package com.ijse.gdse.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDetailsDTO {
    private String orderId;
    private String gemId;
    private int quantity;
    private double price;
}
