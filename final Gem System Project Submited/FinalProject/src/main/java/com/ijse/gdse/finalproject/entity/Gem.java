package com.ijse.gdse.finalproject.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Gem {
    private String gem_id;
    private String gem_name;
    private String gem_color;
    private String size;
    private String price;
    private Integer qty;
    private Boolean is_cetified;
    private String category_id;


}
