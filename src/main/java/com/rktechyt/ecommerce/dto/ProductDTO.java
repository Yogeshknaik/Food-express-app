package com.rktechyt.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private Integer category_id;
    private Double price;
    private String weight;
    private String description;
    private String imageName;
}
