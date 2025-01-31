package com.rktechyt.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rktechyt.ecommerce.model.Product;
import com.rktechyt.ecommerce.repository.ProductRespository;

@Service
public class ProductService {
    @Autowired
    ProductRespository productRespository;

    public void addProduct(Product product){
        productRespository.save(product);
    } 

    public List<Product> getAllProducts(){
        return productRespository.findAll();
    }

    public void removeProductById(long product_id){
        productRespository.deleteById(product_id);
    }

    public Optional<Product> getProductById(long product_id){
        return productRespository.findById(product_id);
    }

    public void updateProductById(Product product){
        productRespository.save(product);
    }

    public List<Product> getAllProductsByCategoryId(int id){
        return productRespository.findAllProductByCategoryId(id);
    }
}
