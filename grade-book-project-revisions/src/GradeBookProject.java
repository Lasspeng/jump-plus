import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import connection.ConnectionManager;
import dao.GradeBookDaoSql;
import model.Course;
import model.Student;
import model.StudentClassInfo;
import model.StudentCourseItem;
import model.Teacher;
import util.ColorsUtility;

public class GradeBookProject {

    public static void main(String[] args) {

        Connection conn;
        GradeBookDaoSql dao = new GradeBookDaoSql();
        try {
            conn = ConnectionManager.getConnection();
            dao.setConnection();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection failed.");
        }

        Scanner sc = new Scanner(System.in);
        int input = 0;
        Student studentAcc = null;
        Teacher teacherAcc = null;
        DecimalFormat df = new DecimalFormat("0.00");
        GradeBookDaoSql.AccountType accountType = null;

        while (true) {
            System.out.println(ColorsUtility.BLUE + "+============================================================================+");
            System.out.println("1. CREATE NEW TEACHER ACCOUNT");
            System.out.println("2. LOGIN AS TEACHER");
            System.out.println("3. LOGIN AS STUDENT");
            System.out.println("4. VIEW COURSES");
            System.out.println("5. EXIT");
            System.out.println("+============================================================================+" + ColorsUtility.RESET);

            try {
                input = sc.nextInt();
                if (input < 1 || input > 5) throw new Exception();
            } catch (InputMismatchException e) {
                System.out.println(ColorsUtility.PURPLE + "Enter a number." + ColorsUtility.RESET);
                input = 0;
                sc.next();
            } catch (Exception e) {
                System.out.println("Not a valid option");
                input = 0;
            }

            switch (input) {
                case 1: {
                    sc.nextLine();
                    System.out.println(ColorsUtility.GREEN + "Enter your full name");
                    String name = sc.nextLine();

                    System.out.println("Enter a username.");
                    String username = sc.nextLine();

                    System.out.println("Enter a password." + ColorsUtility.RESET);
                    String password = sc.nextLine();

                    dao.createTeacherAccount(name, username, password);
                    break;
                }
                case 2: {
                    sc.nextLine();
                    System.out.println(ColorsUtility.GREEN + "Enter your username");
                    String username = sc.nextLine();
                    System.out.println("Enter your password");
                    String password = sc.nextLine();

                    if (dao.doesAccountExist("teacher", username,
                            password)) {
                        accountType = GradeBookDaoSql.AccountType.teacher;
                        teacherAcc = new Teacher(username, password);
                        studentAcc = null;
                        System.out.println("You have successfully logged in" + ColorsUtility.RESET);
                    } else {
                        System.out.println("Credentials were incorrect. Try " +
                                "again.");
                    }
                    break;
                }
                case 3: {
                    sc.nextLine();
                    System.out.println(ColorsUtility.GREEN + "Enter your username");
                    String username = sc.nextLine();
                    System.out.println("Enter your password");
                    String password = sc.nextLine();

                    if (dao.doesAccountExist("student", username,
                            password)) {
                        accountType = GradeBookDaoSql.AccountType.student;
                        studentAcc = new Student(username, password);
                        teacherAcc = null;
                        System.out.println("You have successfully logged in" + ColorsUtility.RESET);
                    } else {
                        System.out.println("Credentials were incorrect. Try " +
                                "again."); 
                    }
                    break;
                }
                case 4: {
                    if (teacherAcc == null && studentAcc == null) {
                        System.out.println(ColorsUtility.PURPLE + "You must be logged in to view " +
                                "your courses" + ColorsUtility.RESET);
                        break;
                    }

                    int choice = 0;
                    int lastCourse = 0;

                    if (studentAcc != null) {
                        do {
                            System.out.println(ColorsUtility.BLUE + "+============================================================================+");
                            System.out.printf("| %-30s | %-12s |%n", "Course " +
                            "Name", "Term");
                            System.out.println("+============================================================================+");
                            for (StudentCourseItem course : studentAcc.getCourseList()) {
                                System.out.printf("| %-30s | %-12s |%n",
                                course.getId() + ". " + course.getCourseName(), course.getTerm() + ColorsUtility.RESET);
                                lastCourse = course.getId();
                            }
                            System.out.println();
                            System.out.println(ColorsUtility.PURPLE + "Select a course to view your grade" + ColorsUtility.RESET);
                            System.out.println();
                            System.out.println(ColorsUtility.BLUE + (lastCourse + 1) + ". EXIT" + ColorsUtility.RESET);

                            int classId = sc.nextInt();
                            if (classId == lastCourse + 1) {
                                break;
                            }

                            if (classId > 0 && classId < lastCourse + 1) {
                                float grade = studentAcc.showGrade(studentAcc.getId(), classId);
                                System.out.println(ColorsUtility.PURPLE + "Your grade in this course is: " + Math.round(grade * 100.0) / 100.0 + ColorsUtility.RESET);
                                
                            }

                        } while (choice == 1);
                        break;
                    } else {
                        do {
                            System.out.println(ColorsUtility.BLUE + "+============================================================================+");
                            System.out.printf("| %-30s | %-12s |%n", "Course " +
                                        "Name", "Term");
                            System.out.println("+============================================================================+");
                            for (Course course : teacherAcc.getCourseList()) {
                                    System.out.printf("| %-30s | %-12s |%n",
                                            course.getId() + ". " + course.getCourseName(), course.getTerm() + ColorsUtility.RESET);
                                    lastCourse = course.getId();
                            }
                            System.out.println();
                            System.out.println(ColorsUtility.PURPLE + "Select the course you want to " +
                                    "view" +
                                    "." + ColorsUtility.RESET);
                            System.out.println();
                            System.out.println(ColorsUtility.BLUE + (lastCourse + 1) + ". Create a new" +
                                    " course");
                            System.out.println((lastCourse + 2) + ". EXIT" + ColorsUtility.RESET);
    
                            int classId = sc.nextInt();
                            if (classId == lastCourse + 1) {
                                sc.nextLine();
                                System.out.println(ColorsUtility.GREEN + "Enter the name of the new " +
                                        "course." + ColorsUtility.RESET);
                                String newCourseName = sc.nextLine();
                                System.out.println(ColorsUtility.GREEN + "Enter the term this new " +
                                        "course will take place in." + ColorsUtility.RESET);
                                String newCourseTerm = sc.nextLine();
    
                                teacherAcc.createNewCourse(newCourseName,
                                        newCourseTerm);
                                break;
                            }
                            if (classId == lastCourse + 2) break;
    
    
    
    
                            List<StudentClassInfo> classroom =
                                    new ArrayList<>();
                            int orderChoice = 0;
    
                            System.out.println(ColorsUtility.GREEN + "Sort classroom by name or " +
                                    "current grade");
                            System.out.println("1. Name");
                            System.out.println("2. Current Grade" + ColorsUtility.RESET);
                            orderChoice = sc.nextInt();
                            if (orderChoice == 1) {
                                classroom = dao.getClassroomByName(classId);
                            } else if (orderChoice == 2){
                                classroom = dao.getClassroomByGrade(classId);
                            }
                            if (classroom.isEmpty()) {
                                System.out.println("This class currently has no students");
                                break;
                            }
    
                            double aggregateGrade = 0.0;
                            double medianGrade = 0.0;
                            List<Double> values = new ArrayList<>();
                            for (StudentClassInfo student : classroom) {
                                aggregateGrade += student.getGrade();
                                values.add(student.getGrade());
                            }
                            double averageGrade =
                            Math.round(aggregateGrade / classroom.size() * 100.0) / 100.0 ;
    
                            Collections.sort(values);
                            if (values.size() % 2 == 1)
                                medianGrade = 
                                        values.get((values.size() + 1) / 2 - 1);
                            else {
                                medianGrade = values.get(values.size() / 2 - 1);
                                medianGrade = values.get(values.size() / 2);
    
                            }
    
                            System.out.println(ColorsUtility.BLUE + "+============================================================================+");
                            System.out.printf("| %-30s | %-12s | %12s " +
                                            "|%n", "Student Name", "Grade",
                                    "Graded Assignments");
                            System.out.println("+============================================================================+");
                            int counter = 1;
                            for (StudentClassInfo student : classroom) {
                                System.out.printf("| %-30s | %-12s | %12s " +
                                                "|%n",
                                        counter + ". " + student.getName(),
                                        Math.round(student.getGrade() * 100.0) / 100.0 , student.getGradeCount());
                                counter++;
                            }
                            System.out.println();
                            System.out.println("| Average Grade: " + averageGrade);
                            System.out.println("| Median Grade: " + medianGrade + ColorsUtility.RESET);
                            System.out.println();
                            System.out.println(ColorsUtility.PURPLE + "Select a student to change " +
                                    "their grades or remove them from the course" + ColorsUtility.RESET);
                            System.out.println();
                            System.out.println(ColorsUtility.BLUE + counter + ". Add student to course");
                            System.out.println((counter + 1) + ". EXIT" + ColorsUtility.RESET);
    
                            int studentChoice = sc.nextInt();
                            int studentChoiceId = 0;
                            if (studentChoice < counter) {
                            studentChoiceId = classroom.get(studentChoice - 1).getStudentId();
                            }
                            if (studentChoice == counter) {
    
                                System.out.println(ColorsUtility.BLUE + "+============================================================================+");
                                System.out.printf("| %-30s | %-12s |%n", "Student" +
                                        " Name", "Major");
                                System.out.println("+============================================================================+");
    
                                List<Student> students = dao.getAllStudents();
                                for (Student student : students) {
                                    System.out.printf("| %-30s | %-12s |%n",
                                            student.getId() + ". " + student.getName(),
                                            student.getMajor());
                                }
                                System.out.println((students.size() + 1) + ". " +
                                        "EXIT" + ColorsUtility.RESET);
                                System.out.println(ColorsUtility.GREEN + "Select student to add.");
                                int studentAddChoice = sc.nextInt();
                                if (studentAddChoice == students.size() + 1) break;
                                teacherAcc.addStudentToCourse(studentAddChoice,
                                        classId);
                            } else if (studentChoice < counter) {
                                System.out.println("Do you want to update this " +
                                        "student's grade or remove them from the " +
                                        "course?");
                                System.out.println("1. Update grade");
                                System.out.println("2. Remove from course");
                                int newChoice = sc.nextInt();
    
                                if (newChoice == 1) {
                                System.out.println("What new grade do you want to" +
                                        " give (in decimal format)?" + ColorsUtility.RESET);
                                double newGrade = sc.nextDouble();
                                teacherAcc.addGrade(newGrade, studentChoiceId,
                                        classId);
                                break;
                                } else if (newChoice == 2) {
                                    teacherAcc.removeStudentFromCourse(studentChoiceId, classId);
                                    break;
                                }
                            } else break;
                        }
                        while (choice == 1);
                        break;
                    }

                }
                case 5: {
                    System.out.println("Goodbye!");
                    return;
                }
                default: continue;
            }
        }
    }

}
