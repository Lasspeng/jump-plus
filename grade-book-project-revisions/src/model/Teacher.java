package model;

import connection.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Teacher {

    private Connection conn;

    private int id;
    private String name;
    private String username;
    private String password;
    private List<Course> courseList;

    public Teacher(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
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

    public Teacher(String username, String password) {
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
            ResultSet rs = stmt.executeQuery("SELECT id FROM teacher WHERE " +
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
//        if (!(getCourseList().isEmpty())) getCourseList().clear();
        try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM course WHERE " +
                "teacher_id = " + this.id);
        ) {
            while (rs.next()) {
                int accountId = rs.getInt("id");
                String courseName = rs.getString("course_name");
                String term = rs.getString("term");
                int teacherId = rs.getInt("teacher_id");

                Course course = new Course(accountId, courseName, term,
                        teacherId);
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createCourse(String courseName, String term) {
        try (Statement stmt = conn.createStatement()) {
            int created = stmt.executeUpdate("INSERT INTO course values(null," +
                    " " + "'" + courseName + "', " + "'" + term + "')");

            if (created == 0) {
                System.out.println("Course could not be created. Try again.");
                return false;
            } else {
                System.out.println("Course successfully created.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addStudentToCourse(int studentId, int courseId) {
        try (Statement stmt = conn.createStatement()) {
            int added = stmt.executeUpdate("INSERT INTO student_course VALUES" +
                    "(null, " + studentId + ", " + courseId + ", " + 0.0 + "," +
                    " " + 0 + ")");

            if (added == 0) {
                System.out.println("Student could not be added. Try again.");
            } else {
                System.out.println("Student has been successfully added to " +
                        "the course.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Student could not be added. Try again.");
        }
    }

    public void addGrade(double newGrade, int studentId, int courseId) {
        StudentCourse studentCourse = null;

        try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM student_course WHERE " +
                "student_id = " + studentId);
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int studentCourseId = rs.getInt("student_id");
                int newCourseId = rs.getInt("course_id");
                double grade = rs.getDouble("grade");
                int gradeCount = rs.getInt("grade_count");
//                System.out.println("result set gradeCount: " + gradeCount);
//                System.out.println("result set studentId: " + studentCourseId);
//                System.out.println("method studentId: " + studentId);
//                System.out.println("result set courseId: " + newCourseId);
//                System.out.println("method courseId: " + courseId);

                studentCourse = new StudentCourse(id, studentCourseId,
                        newCourseId, grade, gradeCount);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        int newGradeCount = studentCourse.getGradeCount() + 1;
//        System.out.println("Checking grade count again: " + studentCourse.getGradeCount());
//        System.out.println("Checking updated grade count: " + newGradeCount);

        double newGradeAvg =
                ((studentCourse.getGrade() * studentCourse.getGradeCount()) + newGrade) / newGradeCount;
        try (Statement stmt = conn.createStatement();) {
            int updated = stmt.executeUpdate("UPDATE student_course SET grade" +
                    " = " + newGradeAvg + ", grade_count = " + newGradeCount + " WHERE student_id = " + studentId + " AND course_id = " + courseId);

            if (updated != 0) {
                System.out.println("Grade has been successfully added.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Grade could not be added. Try again.");
        }
    }

    public void removeStudentFromCourse(int studentId, int courseId) {
        try (Statement stmt = conn.createStatement();) {
            int deleted = stmt.executeUpdate("DELETE FROM student_course " +
                    "WHERE student_id = " + studentId + " AND course_id = " + courseId);

            if (deleted != 0) {
                System.out.println("Student successfully removed from course.");
            } else {
                System.out.println("Student could not be removed from course." +
                        " Try  again");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNewCourse(String name, String term) {
        try (Statement stmt = conn.createStatement()) {
            int created = stmt.executeUpdate("INSERT INTO course VALUES(null," +
                    " '" + name + "', '" + term + "', " + getId() + ")");

            if (created != 0) {
                System.out.println("Course has been successfully created.");
            } else {
                System.out.println("Course not be created. Try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Course not be created. Try again.");
        }
        getCourseList().clear();
        createCourseList();
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
        return "Teacher{" +
                "conn=" + conn +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", courseList=" + courseList +
                '}';
    }
}
