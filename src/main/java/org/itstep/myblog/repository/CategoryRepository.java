package org.itstep.myblog.repository;

import org.itstep.myblog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category getByName(String category);
    List<Category> getByMenu(String menu);
    Category getById(Long id);
}
