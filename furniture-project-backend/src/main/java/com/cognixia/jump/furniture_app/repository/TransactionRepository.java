package com.cognixia.jump.furniture_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.furniture_app.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(nativeQuery = true, value = "SELECT product_id, quantity, cost FROM transaction WHERE user_id = ?1 ORDER BY transaction_number DESC")
    public List<Transaction> allTransactionsByUser(int id);
}
