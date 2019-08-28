package org.itstep.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.itstep.myblog.entities.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String page;
    private String name;
    private String text;
    private Double quantity;
    private String selectQuantity;
    private Double price;
    private String selectPrice;
    private String categoryName;

    public ProductDTO(Product product) {
        this.id = product.getId ();
        this.page = product.getPage ();
        this.name = product.getName ();
        this.text = product.getText ();
        this.quantity = product.getQuantity ();
        this.selectQuantity = product.getSelectQuantity ();
        this.price = product.getPrice ();
        this.selectPrice = product.getSelectPrice ();
        this.categoryName = product.getCategory ().getName ();
    }
}
