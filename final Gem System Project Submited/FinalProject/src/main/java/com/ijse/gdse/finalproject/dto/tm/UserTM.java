package com.ijse.gdse.finalproject.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTM {
    private String userId;
    private String username;
    private String password;
    private String role;
    private String confirmPassword;
}
