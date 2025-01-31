package com.rktechyt.ecommerce.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rktechyt.ecommerce.dto.ProductDTO;
import com.rktechyt.ecommerce.model.Category;
import com.rktechyt.ecommerce.model.Product;
import com.rktechyt.ecommerce.service.CategoryService;
import com.rktechyt.ecommerce.service.ProductService;

@Controller
public class AdminController {
    public String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;


    
    @GetMapping("/error")
    public String error(){
        return "error404";
    }

    // Admin Section
    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    // Category Section
    @GetMapping("/admin/categories")
    public String categories(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCategoriesAdd(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCategoriesAdd(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategoryById(@PathVariable(name = "id") int category_id){

        categoryService.removeCategoryById(category_id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategoryById(@PathVariable(name ="id") int category_id, Model model){
        Optional<Category> category = categoryService.getCategoryById(category_id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        } else {
            return "error404";
        }
    }

    // Product Section
    @GetMapping("/admin/products")
    public String products(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String getProductsAdd(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String postProductAdd(@ModelAttribute(name = "productDTO") ProductDTO productDTO, @RequestParam("productImage") MultipartFile file,@RequestParam("imgName") String imgName) throws IOException {
        // converting object of productDTO to product type.
        Product product = new Product();

        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategory_id().intValue()).get());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());

        // setting Image UUID
        String imageUUID;
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProductById(@PathVariable(name = "id") int product_id){
        productService.removeProductById(product_id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProductById(@PathVariable(name = "id") int product_id, Model model){
        Product product = productService.getProductById(product_id).get();
        if(product.getId() != null){
            ProductDTO productDTO = new ProductDTO();

            productDTO.setId(product.getId());
            productDTO.setCategory_id(product.getCategory().getId());
            productDTO.setDescription(product.getDescription());
            productDTO.setImageName(product.getImageName());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setWeight(product.getWeight());

            model.addAttribute("productDTO", productDTO);
            model.addAttribute("categories", categoryService.getAllCategory());
            return "productsAdd";
        } else {
            return "error404";
        }
    }
}
