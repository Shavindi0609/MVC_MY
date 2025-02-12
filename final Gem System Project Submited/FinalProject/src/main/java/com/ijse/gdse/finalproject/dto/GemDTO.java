package com.ijse.gdse.finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class GemDTO {
    private String gem_id;
    private String gem_name;
    private String gem_color;
    private String size;
    private String price;
    private Integer qty;
    private Boolean is_cetified;
    private String category_id;


}
