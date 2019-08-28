package org.itstep.myblog.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String menu;
    private String name;
    @OneToMany(targetEntity = Product.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private List<Product> products;
}
