package org.itstep.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.itstep.myblog.entities.Product;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetProductsResponse {
    private String status;
    private String message;
    private List<ProductDTO> data;
}
