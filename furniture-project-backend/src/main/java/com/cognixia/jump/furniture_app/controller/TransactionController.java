package com.cognixia.jump.furniture_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.furniture_app.exception.ResourceNotFoundException;
import com.cognixia.jump.furniture_app.model.Product;
import com.cognixia.jump.furniture_app.model.Transaction;
import com.cognixia.jump.furniture_app.model.User;
import com.cognixia.jump.furniture_app.repository.ProductRepository;
import com.cognixia.jump.furniture_app.repository.TransactionRepository;
import com.cognixia.jump.furniture_app.repository.UserRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api")
public class TransactionController {
    
    @Autowired
    TransactionRepository repo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    ProductRepository productRepo;

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return repo.findAll();
    }

    @GetMapping("/transactions/{id}")
    public List<Transaction> getTransactionsById(@PathVariable int id) throws ResourceNotFoundException {

        Optional<User> user = userRepo.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User", id);
        } else {
            return repo.allTransactionsByUser(id);
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {

        transaction.setTransactionNumber(null);
        Transaction createdTransaction = repo.save(transaction);

        // String stringProductId = "" + transaction.getProductId();
        // Integer numberProductId = Integer.valueOf(stringProductId);
        // Optional<Product> product = productRepo.findById(numberProductId);
        // Integer oldStock = product.get().getStock();
        // product.get().setStock(oldStock - transaction.getQuantity());
        // productRepo.save(product.get());
        Optional<Product> product = productRepo.findById(transaction.getProductId());
        Integer oldStock = product.get().getStock();
        product.get().setStock(oldStock - transaction.getQuantity());
        productRepo.save(product.get());

        return ResponseEntity.status(201).body(createdTransaction);
    }
}
