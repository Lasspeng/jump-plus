package Dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import Connection.ConnectionManager;
import Exception.UserNotFoundException;
import model.Customer;
import util.ColorsUtility;

public class CustomerDaoSql implements CustomerDao {

    private Connection conn;

    @Override
    public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
        conn = ConnectionManager.getConnection();
    }

    @Override
    public Optional<Customer> checkCustomer(String username, String password) {


        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Customer WHERE username = ? AND password = ?")) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            int id = 0;
            String uName = "";
            String pwd = "";
            Float monthlyBudget = 0.0F;
            Float yearlyBudget = 0.0F;

            while (rs.next()) {
                id = rs.getInt("id");
                uName = rs.getString("username");
                pwd = rs.getString("password");
                monthlyBudget = rs.getFloat("monthly_budget");
                yearlyBudget = rs.getFloat("yearly_budget");
                
            }
            Customer cust = new Customer(id, uName, pwd, monthlyBudget, yearlyBudget);

            return Optional.of(cust);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    @Override
    public Customer signIn(Scanner scan) {

        String username = "";
        String password = "";
        Optional<Customer> currUser;
        
        do {
            System.out.print(ColorsUtility.GREEN + "Username: " + ColorsUtility.RESET);
			username = scan.nextLine();
			System.out.print(ColorsUtility.GREEN + "Password: " + ColorsUtility.RESET);
			password = scan.nextLine();
			currUser = checkCustomer(username, password);
            System.out.println();

            if (currUser.get().getUsername() == "") {
                System.out.println("The input you entered does not match with the database. Please try again.");
            }
        } while (currUser.get().getUsername() == "");
        return currUser.get();
    }

    @Override
    public void signUp(Scanner scan) {

        System.out.print(ColorsUtility.GREEN + "Username: " + ColorsUtility.RESET);
		String username = scan.nextLine();
		System.out.print(ColorsUtility.GREEN + "Password: " + ColorsUtility.RESET);
		String password = scan.nextLine();

        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Customer VALUES(null, ?, ?, 0, 0)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            System.out.println();
            
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Account has been successfully created");
            } else {
                System.out.println("Account could not be created. Please try again");
            }
        } catch (SQLException e) {
            System.out.println("Username has already been taken");
        }
        
    }

    @Override
    public void createExpense(Scanner scan, int userId) {

        System.out.print(ColorsUtility.GREEN + "Expense: " + ColorsUtility.RESET);
        Float expense = scan.nextFloat();
        scan.nextLine();

        System.out.print(ColorsUtility.GREEN + "Date: " + ColorsUtility.RESET);
        String date = scan.nextLine();
        System.out.println();

        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Account VALUES(null, ?, ?, ?)")) {

            pstmt.setInt(1, userId);
            pstmt.setFloat(2, expense);
            pstmt.setString(3, date);

            if (pstmt.executeUpdate() > 0) {
                System.out.println(ColorsUtility.PURPLE + "Expense successfully added" + ColorsUtility.RESET);
            } else {
                System.out.println("Expense could not be added. Please try again.");
            }

        } catch (SQLException e) {
            System.out.println("Expense could not be added. Please try again.");
        }
        
    }

    @Override
    public void deleteExpense(Scanner scan, int userId) {
        
        viewRecentExpenses(scan, userId);
        System.out.println("Enter the id of the expense you'd like to delete");
        int expenseId = scan.nextInt();
        scan.nextLine();

        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Account WHERE id = ? AND customer_id = ?")) {
            pstmt.setInt(1, expenseId);
            pstmt.setInt(2, userId);
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Expense successfully deleted");
            } else {
                System.out.println("Expense could not be deleted");
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Expense could not be deleted");
        }
        
    }

    @Override
    public void setBudget(Scanner scan, int userId) {

        System.out.println();
        System.out.println();
        System.out.println(ColorsUtility.BLUE + "1. Set Monthly Budget \t 2. Set Yearly Budget" + ColorsUtility.RESET);
        int option = scan.nextInt();
        scan.nextLine();
        
        if (option == 1) {
            System.out.print(ColorsUtility.GREEN + "Monthly Budget Amount: " + ColorsUtility.RESET);
            float budget = scan.nextFloat();
            scan.nextLine();
            try (PreparedStatement pstmt = conn.prepareStatement("UPDATE Customer SET monthly_budget = ? WHERE id = ?")) {
                pstmt.setFloat(1, budget);
                pstmt.setInt(2, userId);
                if (pstmt.executeUpdate() > 0) {
                    System.out.println("Budget successfully updated");
                } else {
                    System.out.println("Budget could not be updated");
                }
            } catch (Exception e) {
                System.out.println("Budget could not be updated");
            }
        } else if (option == 2) {
            System.out.print(ColorsUtility.GREEN + "Monthly Budget Amount: " + ColorsUtility.RESET);
            float budget = scan.nextFloat();
            scan.nextLine();
            try (PreparedStatement pstmt = conn.prepareStatement("UPDATE Customer SET yearly_budget = ? WHERE id = ?")) {
                pstmt.setFloat(1, budget);
                pstmt.setInt(2, userId);
                if (pstmt.executeUpdate() > 0) {
                    System.out.println("Budget successfully updated");
                } else {
                    System.out.println("Budget could not be updated");
                }
            } catch (Exception e) {
                System.out.println("Budget could not be updated");
            }
        }
    }

    @Override
    public void viewRecentExpenses(Scanner scan, int userId) {

        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Account WHERE customer_id = ? ORDER BY date_incurred DESC LIMIT 5")) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            System.out.printf(ColorsUtility.BLUE + "%20s %20s %20s\n","Expense ID","Expense Amount", "Date Incurred" + ColorsUtility.RESET);
            System.out.println("-------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                int custId = rs.getInt("customer_id");
                Float expense = rs.getFloat("expense");
                Date date = rs.getDate("date_incurred");
                System.out.printf(ColorsUtility.BLUE + "%20s %20s %20s\n", id, expense, date + ColorsUtility.RESET);
            }
            System.out.println("-------------------------------------------------------------------");
            System.out.println();
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Recent expenses could not be viewed");
        }
        
    }

    @Override
    public void budgetVsActual(Scanner scan, int userId) {

        System.out.println();
        System.out.println(ColorsUtility.GREEN + "1. Monthly Budget vs Actual" + ColorsUtility.RESET);
        System.out.println(ColorsUtility.GREEN + "2. Yearly Budget vs Actual" + ColorsUtility.RESET);
        int choice = scan.nextInt();
        scan.nextLine();

        if (choice == 1) {
            System.out.println("Month: ");
            int month = scan.nextInt();
            scan.nextLine();

            try (PreparedStatement pstmt = conn.prepareStatement("SELECT C.monthly_budget, ROUND(SUM(A.expense), 2) AS 'Total_Expenses', ROUND(C.monthly_budget - ROUND(SUM(A.expense)), 2)  AS 'Remaining_$', ROUND(C.monthly_budget / ROUND(SUM(A.expense), 2), 2) AS 'Ratio' FROM Customer C JOIN Account A ON C.id = A.customer_id WHERE MONTH(A.date_incurred) = ? && C.id = ? GROUP BY C.monthly_budget")) {
                pstmt.setInt(1, month);
                pstmt.setInt(2, userId);
                ResultSet rs = pstmt.executeQuery();

                System.out.printf(ColorsUtility.BLUE + "%20s %20s %20s %20s\n","Budget", "Actual", "Remaining $", "Ratio" + ColorsUtility.RESET);
                System.out.println("---------------------------------------------------------------------------------------------");

                while (rs.next()) {
                    float budget = rs.getFloat("C.monthly_budget");
                    float actual = rs.getFloat("Total_Expenses");
                    float remaining = rs.getFloat("Remaining_$");
                    float ratio = rs.getFloat("Ratio");

                    System.out.printf(ColorsUtility.BLUE + "%20s %20s %20s %20s\n",budget, actual, remaining, ratio + ColorsUtility.RESET);
                }
                System.out.println("---------------------------------------------------------------------------------------------");
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (choice == 2) {
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT C.yearly_budget, ROUND(SUM(A.expense), 2) AS 'Total_Expenses', ROUND(C.yearly_budget - ROUND(SUM(A.expense)), 2)  AS 'Remaining_$', ROUND(C.yearly_budget / ROUND(SUM(A.expense), 2), 2) AS 'Ratio' FROM Customer C JOIN Account A ON C.id = A.customer_id WHERE C.id = ? GROUP BY C.yearly_budget")) {
                pstmt.setInt(1, userId);
                ResultSet rs = pstmt.executeQuery();

                System.out.printf(ColorsUtility.BLUE + "%20s %20s %20s %20s\n","Budget", "Actual", "Remaining $", "Ratio" + ColorsUtility.RESET);
                System.out.println("---------------------------------------------------------------------------------------------");

                while (rs.next()) {
                    float budget = rs.getFloat("C.yearly_budget");
                    float actual = rs.getFloat("Total_Expenses");
                    float remaining = rs.getFloat("Remaining_$");
                    float ratio = rs.getFloat("Ratio");

                    System.out.printf(ColorsUtility.BLUE + "%20s %20s %20s %20s\n",budget, actual, remaining, ratio + ColorsUtility.RESET);
                }
                System.out.println("---------------------------------------------------------------------------------------------");
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        
    }

    @Override
    public void displayCustomer(Customer customer) {

        System.out.println();
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Customer WHERE id = ?")) {
            pstmt.setInt(1, customer.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                Float mBudget = rs.getFloat("monthly_budget");
                Float yBudget = rs.getFloat("yearly_budget");

                System.out.println(ColorsUtility.PURPLE + "User id: " + id + ColorsUtility.RESET);
                System.out.println(ColorsUtility.PURPLE + "Username: " + username + ColorsUtility.RESET);
                System.out.println(ColorsUtility.PURPLE + "Password: " + "*******" + ColorsUtility.RESET);
                System.out.println(ColorsUtility.PURPLE + "Monthly Budget: " + mBudget + ColorsUtility.RESET);
                System.out.println(ColorsUtility.PURPLE + "Yearly Budget: " + yBudget + ColorsUtility.RESET);
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
