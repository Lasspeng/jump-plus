package model;

public class Course {

    private int id;
    private String courseName;
    private String term;
    private int teacherId;

    public Course(int id, String courseName, String term, int teacherId) {
        this.id = id;
        this.courseName = courseName;
        this.term = term;
        this.teacherId = teacherId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", term='" + term + '\'' +
                ", teacherId=" + teacherId +
                '}';
    }
}
