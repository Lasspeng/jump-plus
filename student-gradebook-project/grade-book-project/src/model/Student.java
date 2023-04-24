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
    private List<Course> courseList;

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
//        createCourseList();
    }

    public Student(String username, String password) {
        this.username = username;
        this.password = password;

        try {
            this.conn = ConnectionManager.getConnection();
        } catch(Exception e) {
            e.printStackTrace();
        }
        createCourseList();
    }

    public void createCourseList() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM course WHERE " +
                     "student_id = " + getId());
             ) {
            while (rs.next()) {
                int accountId = rs.getInt("id");
                String courseName = rs.getString("course_name");
                String term = rs.getString("term");
                int studentId = rs.getInt("student_id");

                Course course = new Course(accountId, courseName, term,
                        studentId);
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
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
