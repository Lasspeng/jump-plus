import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import Dao.CustomerDaoSql;
import Exception.UserNotFoundException;
import model.Customer;
import util.ColorsUtility;

public class ExpenseTrackingApplication {
    public static void main(String[] args) {
        
        CustomerDaoSql custDao = new CustomerDaoSql();
        try {
            custDao.setConnection();
            System.out.println(ColorsUtility.PURPLE + "Welcome to the Expense Tracking App!" + ColorsUtility.RESET);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        try (Scanner scan = new Scanner(System.in)) {
            Customer customer;
            String choice;
            do {
                System.out.println("-------------------------------------------------------------------");
                System.out.printf(ColorsUtility.BLUE + "%15s %15s %15s\n", "1. Sign Up", "2. Login", "0. Exit" + ColorsUtility.RESET);
                System.out.println("-------------------------------------------------------------------");
                choice = scan.nextLine();

                if (choice.equals("1")) {
                    custDao.signUp(scan);
                } else if (choice.equals("2")) {
                    customer = custDao.signIn(scan);

                    String newChoice;
                    do {
                        System.out.println("-------------------------------------------------------------------------------------------");
                        System.out.printf(ColorsUtility.BLUE + "%20s %20s %20s %30s\n", "1. Add Expense", "2. Remove Expense", "3. Set Budget", "4. Upcoming Expenses"+ ColorsUtility.RESET);
                        System.out.println();
                        System.out.printf(ColorsUtility.BLUE + "%20s %30s %20s\n", "5. Budget vs Actual", "6. Account Information", "0. Exit" + ColorsUtility.RESET);
                        System.out.println("-------------------------------------------------------------------------------------------");

                        newChoice = scan.nextLine();

                        if (newChoice.equals("1")) {
                            custDao.createExpense(scan, customer.getId());
                        } else if (newChoice.equals("2")) {
                            custDao.deleteExpense(scan, customer.getId());
                        } else if (newChoice.equals("3")) {
                            custDao.setBudget(scan, customer.getId());
                        } else if (newChoice.equals("4")) {
                            custDao.viewRecentExpenses(scan, customer.getId());
                        } else if (newChoice.equals("5")) {
                            custDao.budgetVsActual(scan, customer.getId());
                        } else if (newChoice.equals("6")) {
                            custDao.displayCustomer(customer);
                        }
                    } while (!newChoice.equals("0"));
                }
            } while (!choice.equals("0"));
        }
    }
}
