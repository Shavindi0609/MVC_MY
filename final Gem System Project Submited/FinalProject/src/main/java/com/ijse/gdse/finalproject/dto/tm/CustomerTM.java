package com.ijse.gdse.finalproject.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerTM {
        private String customerId;
        private String name;
        private String nic;
        private String email;
        private String phone;
        private String address;
        private String userId;
}

