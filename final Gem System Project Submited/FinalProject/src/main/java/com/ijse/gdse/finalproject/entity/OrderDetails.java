package com.ijse.gdse.finalproject.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDetails {
    private String orderId;
    private String gemId;
    private int quantity;
    private double price;
}
