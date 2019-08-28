package org.itstep.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.itstep.myblog.entities.Category;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryNameDTO {
    private String name;
    public CategoryNameDTO(Category category){
        this.name = category.getName ();
    }
}
