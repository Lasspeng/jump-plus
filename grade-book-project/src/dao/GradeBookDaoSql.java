package dao;

import connection.ConnectionManager;
import model.Student;
import model.StudentClassInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GradeBookDaoSql {

    public enum AccountType {
        student, teacher;
    }
    private Connection conn;

    public void setConnection() throws FileNotFoundException,
            ClassNotFoundException, IOException, SQLException {
        conn = ConnectionManager.getConnection();
    }

    public boolean doesAccountExist(String type, String username,
                                    String password) {
        try (Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + type + " " +
                    "WHERE username = '" + username + "' AND password = '" + password + "'");

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<StudentClassInfo> getClassroomByName(int courseId) {
        List<StudentClassInfo> classroom = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT s.id, s" +
                    ".name, sc.grade, " +
                    "sc" +
                    ".grade_count FROM student_course sc JOIN student s ON sc" +
                    ".student_id = s.id WHERE sc.course_id = " + courseId + " ORDER BY s.name");


            while (rs.next()) {
                int id = rs.getInt("s.id");
                String name = rs.getString("s.name");
                double grade = rs.getDouble("sc.grade");
                int gradeCount = rs.getInt("sc.grade_count");

                StudentClassInfo student = new StudentClassInfo(id, name, grade,
                        gradeCount);
                classroom.add(student);
            }
            return classroom;

        } catch (SQLException e) {
            e.printStackTrace();
            return classroom;
        }
    }

    public List<StudentClassInfo> getClassroomByGrade(int courseId) {
        List<StudentClassInfo> classroom = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT s.id, s" +
                    ".name, sc.grade, " +
                    "sc" +
                    ".grade_count FROM student_course sc JOIN student s ON sc" +
                    ".student_id = s.id WHERE sc.course_id = " + courseId +
                    " ORDER BY sc.grade DESC");

            while (rs.next()) {
                int id = rs.getInt("s.id");
                String name = rs.getString("s.name");
                double grade = rs.getDouble("sc.grade");
                int gradeCount = rs.getInt("sc.grade_count");

                StudentClassInfo student = new StudentClassInfo(id, name, grade,
                        gradeCount);
                classroom.add(student);
            }
            return classroom;

        } catch (SQLException e) {
            e.printStackTrace();
            return classroom;
        }
    }

    public void createTeacherAccount(String name, String username,
                                     String password) {
        try (Statement stmt = conn.createStatement()) {
            int created =
                    stmt.executeUpdate("INSERT INTO teacher VALUES(null, '" + name + "', '" + username + "', '" + password + "')");

            if (created == 0) {
                System.out.println("Account could not be created. Try again.");
            } else {
                System.out.println("Account successfully created.");
            }
        } catch (SQLException e) {
            System.out.println("Account could not be created. Try again.");
        }
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM student");
        ) {
            while (rs.next()) {
                int studentId = rs.getInt("id");
                String name = rs.getString("name");
                String major = rs.getString("major");
                String username = rs.getString("username");
                String password = rs.getString("password");

                Student student = new Student(studentId, name, major,
                        username, password);
                studentList.add(student);
            }
            return studentList;
        } catch (SQLException e) {
            e.printStackTrace();
            return studentList;
        }
    }

}
