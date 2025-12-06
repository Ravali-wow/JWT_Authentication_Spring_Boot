package com.bootai.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootai.boot.entity.Product;
import com.bootai.boot.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    
    @Autowired
    ProductService productService;

     @GetMapping("/showAll")
     public ResponseEntity<List<Product>> getAll()
     {
        return productService.getAll();
     }

     @GetMapping("/showIt/{id}")
     public ResponseEntity<Product> getIt(@PathVariable int id)
     {
        return productService.getProduct(id);
     }

     @PostMapping("/store")
     public String storeIt(/*@RequestBody*/ Product p)
     {
        System.out.println("Storing Product Details");
        return productService.storeProduct(p);
     }

     @PutMapping("/putIt/{id}")
     public ResponseEntity<String> putIt(@PathVariable int id,@RequestBody Product p)
     {
        return productService.updateProduct(id,p);
     }

     @DeleteMapping("/removeIt/{id}")
     public ResponseEntity<String> removeIt(@PathVariable int id)
     {
        return productService.deleteProduct(id);
     }

}
