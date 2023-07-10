package com.cognixia.jump.furniture_app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import com.cognixia.jump.furniture_app.model.Product;
import com.cognixia.jump.furniture_app.model.Transaction;
import com.cognixia.jump.furniture_app.model.User;
import com.cognixia.jump.furniture_app.model.User.Role;
import com.cognixia.jump.furniture_app.repository.ProductRepository;
import com.cognixia.jump.furniture_app.repository.TransactionRepository;
import com.cognixia.jump.furniture_app.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user1", password = "pw123", roles = "ADMIN")
public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionRepository transactionRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private ProductRepository productRepo;

    @Test
    public void testGetAllTransactions() throws Exception {
        List<Transaction> transactionList = new ArrayList<>();
        User user = new User(1, "Hello", "World", Role.ROLE_USER, true);
        Product product = new Product(1, "Chair", 7, 78.99, "adfakld32589djhfs");
        transactionList.add(new Transaction(1, user, 1, 2, 59.99F));
        transactionList.add(new Transaction(2, user, 1, 1, 23.99F));

        when(transactionRepo.findAll()).thenReturn(transactionList);
        List<Transaction> transactions = transactionController.getAllTransactions();
        assertEquals(transactionList.size(), transactions.size());
    }

    @Test
    public void testGetTransactionsById() throws Exception {
        int id = 1;
        List<Transaction> transList = new ArrayList<>();
        Transaction foundTransaction = new Transaction(1, new User(), 1, 2, 59.99F);
        transList.add(foundTransaction);
        User foundUser = new User(1, "Hello", "World", Role.ROLE_USER, true);

        when(userRepo.findById(id)).thenReturn(Optional.of(foundUser));
        when(transactionRepo.allTransactionsByUser(id)).thenReturn(transList);

        List<Transaction> transactions = transactionController.getTransactionsById(id);
        assertEquals(transList, transactions);
    }

    @Test
    public void testCreateTransaction() throws Exception {
        int id = 1;
        User user = new User(1, "Hello", "World", Role.ROLE_USER, true);
        Product product = new Product(1, "Chair", 7, 78.99, "adfakld32589djhfs");
        Transaction transaction = new Transaction(1, user, 1, 2, 59.99F);

        when(transactionRepo.save(Mockito.any(Transaction.class))).thenReturn(transaction);
        when(productRepo.findById(id)).thenReturn(Optional.of(product));
        when(productRepo.save(Mockito.any(Product.class))).thenReturn(product);

        ResponseEntity<Transaction> result = transactionController.createTransaction(transaction);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
    
}
