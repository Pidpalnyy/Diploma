package org.itstep.myblog.repository;

import org.itstep.myblog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
//    List<Product> getByProduct();
}
