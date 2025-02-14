package com.ijse.gdse.finalproject.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Supplier {
    private String supplierId;
    private String name;
    private String nic;
    private String email;
    private String phone;
    private String address;
}
