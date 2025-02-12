package com.ijse.gdse.finalproject.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CartTM {
    private String gemId;
    private String gemName;
    private int cartQuantity;
    private double unitPrice;
    private double total;
    private Button removeBtn;
}
