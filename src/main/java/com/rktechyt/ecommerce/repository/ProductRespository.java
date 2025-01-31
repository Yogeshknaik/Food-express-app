package com.rktechyt.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rktechyt.ecommerce.model.Product;

@Repository
public interface ProductRespository extends JpaRepository<Product, Long>{
    List<Product> findAllProductByCategoryId(int id);
}
