package com.kunal.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kunal.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
