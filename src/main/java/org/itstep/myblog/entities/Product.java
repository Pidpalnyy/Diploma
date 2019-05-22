package org.itstep.myblog.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(255)")
    private String page;
    @Column(columnDefinition = "VARCHAR(255)")
    private String name;
    @Column(columnDefinition = "TEXT")
    private String text;
    @Column(columnDefinition = "DOUBLE")
    private Double quantity;
    @Column(columnDefinition = "DOUBLE")
    private Double price;

    @ManyToOne(targetEntity = Category.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;


}
