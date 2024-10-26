package com.itsamoilov.blog_Samoilov.repo;

import com.itsamoilov.blog_Samoilov.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
