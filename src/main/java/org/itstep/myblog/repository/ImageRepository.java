package org.itstep.myblog.repository;

import org.itstep.myblog.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Image getById(Long id);
}
