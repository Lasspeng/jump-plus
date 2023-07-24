package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.model.Person;
import com.cognixia.jump.util.ColorsUtility;

public class PersonDaoSql implements PersonDao {

    private Connection conn;

    @Override
    public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
        conn = ConnectionManager.getConnection();
    }

    @Override
    public Person signIn(Scanner scan) {
        String email = "";
        String password = "";
        Optional<Person> currPerson;

        do {
            System.out.print(ColorsUtility.GREEN + "Email: " + ColorsUtility.RESET);
			email = scan.nextLine();
			System.out.print(ColorsUtility.GREEN + "Password: " + ColorsUtility.RESET);
			password = scan.nextLine();
			currPerson = checkPerson(email, password);
            System.out.println();
            
            if (currPerson.isEmpty()) {
                System.out.println("The input you entered does not match with the database. Please try again.");
            }
        } while (currPerson.isEmpty());
        return currPerson.get();
    }

    @Override
    public Optional<Person> checkPerson(String email, String password) {
        
        try(PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Person WHERE email = ? AND password = ?")) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String nEmail = rs.getString("email");
                String nPassword = rs.getString("password");

                return Optional.of(new Person(id, name, nEmail, nPassword));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void signUp(Scanner scan) {

        System.out.print(ColorsUtility.GREEN + "Name: " + ColorsUtility.RESET);
		String name = scan.nextLine();

        boolean doesMatch;
        String email;

        do {
        System.out.print(ColorsUtility.GREEN + "Email: " + ColorsUtility.RESET);
		email = scan.nextLine();

        Pattern pattern = Pattern.compile(Person.PATTERN);
        doesMatch = pattern.matcher(email).find();
        if (!doesMatch) System.out.println("Not a valid email address. Please try again");
        } while (!doesMatch);

        String password;
        String confirmPassword;

        do {
		System.out.print(ColorsUtility.GREEN + "Password: " + ColorsUtility.RESET);
		password = scan.nextLine();
		System.out.print(ColorsUtility.GREEN + "Confirm Password: " + ColorsUtility.RESET);
        confirmPassword = scan.nextLine();
        if (!password.equals(confirmPassword)) System.out.println("Passwords do not match please try again");
        } while (!password.equals(confirmPassword));


        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Person VALUES(null, ?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            System.out.println();

            if (pstmt.executeUpdate() > 0) {
                System.out.println("Account has been successfully created");
            } else {
                System.out.println("Account could not be created. Please try again");
            }
        } catch (SQLException e) {
            System.out.println("Account could not be created. Please try again");
        }
    }

    @Override
    public void viewContacts(Scanner scan, int userId, int choice) {

        String statement;
        if (choice == 1) {
            statement = "SELECT id, name, email, phone_number FROM Contact WHERE person_id = ? ORDER BY name"; 
        } else {
            statement = "SELECT id, name, email, phone_number FROM Contact WHERE person_id = ? ORDER BY id DESC";
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            System.out.printf(ColorsUtility.BLUE + "%10s %20s %20s %25s\n" ,"ID","Name", "Email", "Phone Number" + ColorsUtility.RESET);
            System.out.println("---------------------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                System.out.printf(ColorsUtility.BLUE + "%10s %20s %20s %25s\n" , id, name, email, phoneNumber + ColorsUtility.RESET);
            }
            System.out.println("---------------------------------------------------------------------------------"); 
            System.out.println();
            System.out.println();
        } catch (SQLException e) {
            System.out.println("There are no contacts");
        }
    }

    @Override
    public void addContact(Scanner scan, int userId) {

        System.out.print(ColorsUtility.GREEN + "Name: " + ColorsUtility.RESET);
        String name = scan.nextLine();

        System.out.print(ColorsUtility.GREEN + "Phone Number: " + ColorsUtility.RESET);
        String phoneNumber = scan.nextLine();

        boolean doesMatch;
        String email;

        do {
        System.out.print(ColorsUtility.GREEN + "Email: " + ColorsUtility.RESET);
		email = scan.nextLine();

        Pattern pattern = Pattern.compile(Person.PATTERN);
        doesMatch = pattern.matcher(email).find();
        if (!doesMatch) System.out.println("Not a valid email address. Please try again");
        } while (!doesMatch);


        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Contact VALUES(null, ?, ?, ?, ?)")) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, name);
            pstmt.setString(3, phoneNumber);
            pstmt.setString(4, email);

            if (pstmt.executeUpdate() > 0) {
                System.out.println(ColorsUtility.PURPLE + "Contact successfully added" + ColorsUtility.RESET);
            } else {
                System.out.println("Contact could not be added. Please try again");
            }
        } catch (SQLException e) {
            System.out.println("Contact could not added. Please try again.");
        }
    }

    @Override
    public void updateContact(Scanner scan, int userId) {
        viewContacts(scan, userId, 1);
        System.out.println(ColorsUtility.GREEN + "Enter the id of the contact you'd like to update" + ColorsUtility.RESET);
        int contactId = scan.nextInt();
        scan.nextLine();

        System.out.println("What do you want to change?");
        System.out.println("1. Name");
        System.out.println("2. Phone Number");
        System.out.println("3. Email");
        int choice = scan.nextInt();
        scan.nextLine();

        String statement;
        String newData;
        if (choice == 1) {
            System.out.print("New name: ");
            newData = scan.nextLine();
            statement = "UPDATE Contact SET name = ? WHERE id = ?";
        } else if (choice == 2) {
            System.out.print("New phone number: ");
            newData = scan.nextLine();
            statement = "UPDATE Contact SET phone_number = ? WHERE id = ?";
        } else {
            System.out.print("New email: ");
            newData = scan.nextLine();
            statement = "UPDATE Contact SET email = ? WHERE id = ?";
        }
        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setString(1, newData);
            pstmt.setInt(2, contactId);
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Contact successfully updated");
            } else {
                System.out.println("Contact could not be updated");
            }
            System.out.println();
        }  catch (SQLException e) {
            System.out.println("Contact could not be deleted");
        }
    }

    @Override
    public void removeContact(Scanner scan, int userId) {
        
        viewContacts(scan, userId, 1);
        System.out.println(ColorsUtility.GREEN + "Enter the id of the contact you'd like to update" + ColorsUtility.RESET);
        int contactId = scan.nextInt();
        scan.nextLine(); 

        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Contact WHERE id = ? AND person_id = ?")) {
            pstmt.setInt(1, contactId);
            pstmt.setInt(2, userId);
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Contact successfully deleted");
            } else {
                System.out.println("Contact could not be deleted");
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Contact could not be deleted");
        }
    }
    
}
