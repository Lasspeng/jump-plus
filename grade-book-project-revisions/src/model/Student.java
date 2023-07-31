package model;

import connection.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Student {

    private Connection conn;

    private int id;
    private String name;
    private String major;
    private String username;
    private String password;
    private List<StudentCourseItem> courseList;

    public Student(int id, String name, String major, String username, String password) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.username = username;
        this.password = password;
        this.courseList = new ArrayList<>();

        try {
            this.conn = ConnectionManager.getConnection();
        } catch(Exception e) {
            e.printStackTrace();
        }
       createCourseList();
    }

    public Student(String username, String password) {
        super();
        this.username = username;
        this.password = password;
        this.courseList = new ArrayList<>();

        try {
            this.conn = ConnectionManager.getConnection();
        } catch(Exception e) {
            e.printStackTrace();
        }
        this.id = getAccountId(username, password);
        createCourseList();
    }

    private int getAccountId(String username, String password) {
        int accountId = 0;
        try (Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT id FROM student WHERE " +
                    "username = '" + username + "' AND password = '" + password +
                    "'");
                    while (rs.next()) {
                        accountId = rs.getInt("id");
                    }
                    return accountId;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return accountId;
            }

    public void createCourseList() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT c.id, c.course_name, c.term, sc.student_id FROM course c JOIN student_course sc ON c.id = sc.course_id WHERE sc.student_id = " + getId());
             ) {
            while (rs.next()) {
                int accountId = rs.getInt("c.id");
                String courseName = rs.getString("c.course_name");
                String term = rs.getString("c.term");
                int studentId = rs.getInt("sc.student_id");


                StudentCourseItem course = new StudentCourseItem(accountId, courseName, term,
                        studentId);
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Float showGrade(int id, int classId) {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT grade FROM student_course WHERE student_id = " + id + " AND course_id = " + classId);
             ) {
                float grade = 0;
                while (rs.next()) {
                    grade = rs.getFloat("grade");
                }
                return grade;
             } catch (SQLException e) {
                e.printStackTrace();
                return null;
             }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<StudentCourseItem> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<StudentCourseItem> courseList) {
        this.courseList = courseList;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", major='" + major + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", courseList=" + courseList +
                '}';
    }
}
