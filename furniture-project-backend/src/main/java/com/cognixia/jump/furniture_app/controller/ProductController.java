package com.cognixia.jump.furniture_app.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.furniture_app.exception.ResourceNotFoundException;
import com.cognixia.jump.furniture_app.model.Product;
import com.cognixia.jump.furniture_app.repository.ProductRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api")
public class ProductController {
    
    @Autowired
    ProductRepository repo;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) throws ResourceNotFoundException {

        Optional<Product> product = repo.findById(id);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product", id);
        } else {
            return ResponseEntity.status(200).body(product.get());
        }
    }
    
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {

        product.setId(null);
        
        Product createdProduct = repo.save(product);
        return ResponseEntity.status(201).body(createdProduct);
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @PathVariable int id, @RequestBody Product product) throws ResourceNotFoundException {

        Optional<Product> existingProduct = repo.findById(id);
        if (existingProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product", id);
        } else {
            repo.save(product);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable int id) throws ResourceNotFoundException {

        Optional<Product> existingProduct = repo.findById(id);
        if (existingProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product", id);
        } else {
            repo.delete(existingProduct.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
