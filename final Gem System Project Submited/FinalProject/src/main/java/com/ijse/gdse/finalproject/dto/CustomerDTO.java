package com.ijse.gdse.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDTO {
    private String customerId;
    private String name;
    private String nic;
    private String email;
    private String phone;
    private String address;
    private String userId;
}
