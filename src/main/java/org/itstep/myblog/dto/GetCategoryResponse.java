package org.itstep.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class GetCategoryResponse {
    private String status;
    private String message;
    private List<CategoryNameDTO> data;
}
