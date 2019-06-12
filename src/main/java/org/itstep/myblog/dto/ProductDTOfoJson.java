package org.itstep.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTOfoJson {
    private Long id;
    private String page;
    private String name;
    private String text;
    private Double quantity;
    private Double price;
    private String categoryName;

    public ProductDTOfoJson(ProductDTO productDto) {
        this.id = productDto.getId ();
        this.page = productDto.getPage ();
        this.name = productDto.getName ();
        this.text = productDto.getText ();
        this.quantity = productDto.getQuantity ();
        this.price = productDto.getPrice ();
        this.categoryName = productDto.getCategoryName ();
    }
}
