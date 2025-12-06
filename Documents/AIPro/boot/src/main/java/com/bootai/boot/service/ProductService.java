package com.bootai.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bootai.boot.entity.Product;
import com.bootai.boot.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<List<Product>> getAll()
    {
        List<Product> products=productRepository.findAll();
        if(products!=null)
            return ResponseEntity.status(HttpStatus.OK).body(products);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<Product> getProduct(int id)
    {
        Optional<Product> product=productRepository.findById(id);
        //productRepository.findById(id).orElseThrow(()-> new Exception("Product not found"));

        if(product!=null)
            return ResponseEntity.status(HttpStatus.OK).build();
        else 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public String storeProduct(Product p)
    {
        productRepository.save(p);
        return "Product Details are saved";
    }

    public ResponseEntity<String> updateProduct(int id,Product p)
    {
        Optional<Product> existing=productRepository.findById(id);
        //productRepository.findById(id).orElseThrow(()-> new Exception("Invalid Product id"));
        if(!existing.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        else
        {
            Product old=existing.get();
            old.setName(p.getName());
            old.setQuantity(p.getQuantity());
            old.setVendor(p.getVendor());
            old.setBarcode(p.getBarcode());
            productRepository.save(old);
            return ResponseEntity.status(HttpStatus.OK).body("Product Details Updated");
        }

    }

    public ResponseEntity<String> deleteProduct(int id)
    {
        Optional<Product> existing=productRepository.findById(id);
        //productRepository.findById(id).orElseThrow(()-> new Exception("Invalid Product id"));
        if(!existing.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        else
        {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Product Deleted");
        }

    }


}
