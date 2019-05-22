package org.itstep.myblog.dto;

import lombok.Getter;
import lombok.Setter;
import org.itstep.myblog.entities.Category;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String page;
    private String name;
    private String text;
    private Double quantity;
    private Double price;

//    private Category category;
}
