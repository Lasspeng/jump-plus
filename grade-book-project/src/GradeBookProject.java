import connection.ConnectionManager;
import dao.GradeBookDaoSql;
import model.Course;
import model.Student;
import model.StudentClassInfo;
import model.Teacher;

import java.sql.Connection;
import java.util.*;

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
        GradeBookDaoSql.AccountType accountType = null;

        while (true) {
            System.out.println("+============================================================================+");
            System.out.println("1. CREATE NEW TEACHER ACCOUNT");
            System.out.println("2. LOGIN");
            System.out.println("3. VIEW COURSES");
            System.out.println("4. EXIT");
            System.out.println("+============================================================================+");

            try {
                input = sc.nextInt();
                if (input < 1 || input > 4) throw new Exception();
            } catch (InputMismatchException e) {
                System.out.println("Enter a number.");
                input = 0;
                sc.next();
            } catch (Exception e) {
                System.out.println("Not a valid option");
                input = 0;
            }

            switch (input) {
                case 1: {
                    sc.nextLine();
                    System.out.println("Enter your full name");
                    String name = sc.nextLine();

                    System.out.println("Enter a username.");
                    String username = sc.nextLine();

                    System.out.println("Enter a password.");
                    String password = sc.nextLine();

                    dao.createTeacherAccount(name, username, password);
                    break;
                }
                case 2: {
                    sc.nextLine();
                    System.out.println("Enter your username");
                    String username = sc.nextLine();
                    System.out.println("Enter your password");
                    String password = sc.nextLine();

                    if (dao.doesAccountExist("teacher", username,
                            password)) {
                        accountType = GradeBookDaoSql.AccountType.teacher;
                        teacherAcc = new Teacher(username, password);
                        System.out.println("You have successfully logged in");
                    } else {
                        System.out.println("Credentials were incorrect. Try " +
                                "again.");
                    }
                    break;
                }
                case 3: {
                    if (teacherAcc == null) {
                        System.out.println("You must be logged in to view " +
                                "your courses");
                        break;
                    }

                    int choice = 0;
                    do {
                        System.out.println("+============================================================================+");
                        System.out.printf("| %-30s | %-12s |%n", "Course " +
                                    "Name", "Term");
                        System.out.println("+============================================================================+");
                        for (Course course : teacherAcc.getCourseList()) {
                                System.out.printf("| %-30s | %-12s |%n",
                                        course.getId() + ". " + course.getCourseName(), course.getTerm());
                        }
                        System.out.println();
                        System.out.println("Select the course you want to " +
                                "view" +
                                ".");
                        System.out.println();
                        System.out.println((teacherAcc.getCourseList().size() + 1) + ". Create a new course.");
                        System.out.println((teacherAcc.getCourseList().size() + 2) + ". EXIT");

                        int classId = sc.nextInt();
                        if (classId == teacherAcc.getCourseList().size() + 1) {
                            sc.nextLine();
                            System.out.println("Enter the name of the new " +
                                    "course.");
                            String newCourseName = sc.nextLine();
                            System.out.println("Enter the term this new " +
                                    "course will take place in.");
                            String newCourseTerm = sc.nextLine();

                            teacherAcc.createNewCourse(newCourseName,
                                    newCourseTerm);
                            break;
                        }
                        if (classId == teacherAcc.getCourseList().size() + 2) break;




                        List<StudentClassInfo> classroom =
                                new ArrayList<>();
                        int orderChoice = 0;

                        System.out.println("Sort classroom by name or " +
                                "current grade");
                        System.out.println("1. Name");
                        System.out.println("2. Current Grade");
                        orderChoice = sc.nextInt();
                        if (orderChoice == 1) {
                            classroom = dao.getClassroomByName(classId);
                        } else if (orderChoice == 2){
                            classroom = dao.getClassroomByGrade(classId);
                        }

                        double aggregateGrade = 0.0;
                        double medianGrade = 0.0;
                        List<Double> values = new ArrayList<>();
                        for (StudentClassInfo student : classroom) {
                            aggregateGrade += student.getGrade();
                            values.add(student.getGrade());
                        }
                        double averageGrade =
                                aggregateGrade / classroom.size();

                        Collections.sort(values);
                        if (values.size() % 2 == 1)
                            medianGrade =
                                    values.get((values.size() + 1) / 2 - 1);
                        else {
                            medianGrade = values.get(values.size() / 2 - 1);
                            medianGrade = values.get(values.size() / 2);

                        }

                        System.out.println("+============================================================================+");
                        System.out.printf("| %-30s | %-12s | %12s " +
                                        "|%n", "Student Name", "Grade",
                                "Graded Assignments");
                        System.out.println("+============================================================================+");
                        int counter = 1;
                        for (StudentClassInfo student : classroom) {
                            System.out.printf("| %-30s | %-12s | %12s " +
                                            "|%n",
                                    counter + ". " + student.getName(),
                                    student.getGrade(), student.getGradeCount());
                            counter++;
                        }
                        System.out.println();
                        System.out.println("| Average Grade: " + averageGrade);
                        System.out.println("| Median Grade: " + medianGrade);
                        System.out.println();
                        System.out.println("Select a student to change " +
                                "their grades or remove them from the course");
                        System.out.println();
                        System.out.println(counter + ". Add student to course");
                        System.out.println((counter + 1) + ". EXIT");

                        int studentChoice = sc.nextInt();
                        int studentChoiceId = 0;
                        if (studentChoice < counter) {
                        studentChoiceId = classroom.get(studentChoice - 1).getStudentId();
                        }
                        if (studentChoice == counter) {

                            System.out.println("+============================================================================+");
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
                                    "EXIT");
                            System.out.println("Select student to add.");
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
                                    " give (in decimal format)?");
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
                case 4: {
                    System.out.println("Goodbye!");
                    return;
                }
                default: continue;
            }
        }
    }

}
