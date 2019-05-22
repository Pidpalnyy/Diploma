package org.itstep.myblog.repository;

import org.itstep.myblog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User,Long> {
    User getByName(String name);
}
