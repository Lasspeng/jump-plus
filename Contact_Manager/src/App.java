import java.util.Scanner;

import com.cognixia.jump.dao.PersonDaoSql;
import com.cognixia.jump.model.Person;
import com.cognixia.jump.util.ColorsUtility;

public class App {
    public static void main(String[] args) throws Exception {
        
        PersonDaoSql personDao = new PersonDaoSql();
        try {
            personDao.setConnection();
            System.out.println(ColorsUtility.PURPLE + "Welcome to the Contact Management App!" + ColorsUtility.RESET);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Scanner scan = new Scanner(System.in)) {
            Person person;
            String choice;
            do {
                System.out.println("-------------------------------------------------------------------");
                System.out.printf(ColorsUtility.BLUE + "%15s %15s %18s\n", "1. Sign Up", "2. Login", "0. Exit" + ColorsUtility.RESET);
                System.out.println("-------------------------------------------------------------------");
                choice = scan.nextLine();

                if (choice.equals("1")) {
                    personDao.signUp(scan);
                } else if (choice.equals("2")) {
                    person = personDao.signIn(scan);

                    String newChoice;
                    do {
                        System.out.println("--------------------------------------------------------------------------------------------------------");
                        System.out.printf(ColorsUtility.BLUE + "%20s %20s %20s %20s %20s\n", "1. Add Contact", "2. Update Contact", "3. Delete Contact", "4. View Contacts", "0. Sign Out" + ColorsUtility.RESET);
                        System.out.println("--------------------------------------------------------------------------------------------------------");

                        newChoice = scan.nextLine();
                        
                        if (newChoice.equals("1")) {
                            personDao.addContact(scan, person.getId());
                        } else if (newChoice.equals("2")) {
                            personDao.updateContact(scan, person.getId());
                        } else if (newChoice.equals("3")) {
                            personDao.removeContact(scan, person.getId());
                        } else if (newChoice.equals("4")) {
                            System.out.println("How do you want your contacts sorted?");
                            System.out.println("1. Alphabetically");
                            System.out.println("2. Recency");
                            int orderChoice = scan.nextInt();
                            scan.nextLine();
                            personDao.viewContacts(scan, person.getId(), orderChoice);
                        }
                    } while (!choice.equals("0"));
                } 
            } while (!choice.equals("0"));
        }
    }
}
