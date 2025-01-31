package com.rktechyt.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rktechyt.ecommerce.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
}
