package com.rktechyt.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rktechyt.ecommerce.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    
}
