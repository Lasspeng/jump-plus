package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import com.cognixia.jump.model.Person;

public interface PersonDao {

    public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;

    public Person signIn(Scanner scan);

    public Optional<Person> checkPerson(String email, String password);

    public void signUp(Scanner scan);

    public void viewContacts(Scanner scan, int userId, int choice);

    public void addContact(Scanner scan, int userId);

    public void updateContact(Scanner scan, int userId);

    public void removeContact(Scanner scan, int userId);


    
}
