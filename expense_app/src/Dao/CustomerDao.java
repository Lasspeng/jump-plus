package Dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import Exception.UserNotFoundException;
import model.Customer;

public interface CustomerDao {
    
    public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;

    public Customer signIn(Scanner scan);

    public Optional<Customer> checkCustomer(String username, String password);

    public void signUp(Scanner scan);
    
    public void createExpense(Scanner scan, int userId);

    public void deleteExpense(Scanner scan, int userId);

    public void setBudget(Scanner scan, int userId);

    public void viewRecentExpenses(Scanner scan, int userId);

    public void budgetVsActual(Scanner scan, int userId);

    public void displayCustomer(Customer customer);
}
