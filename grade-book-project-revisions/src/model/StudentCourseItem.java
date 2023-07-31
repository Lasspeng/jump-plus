package model;
import java.util.Objects;

public class StudentCourseItem {
    
    private int id;
    private String courseName;
    private String term;
    private int studentId;


    public StudentCourseItem() {
    }

    public StudentCourseItem(int id, String courseName, String term, int studentId) {
        this.id = id;
        this.courseName = courseName;
        this.term = term;
        this.studentId = studentId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTerm() {
        return this.term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getStudentId() {
        return this.studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public StudentCourseItem id(int id) {
        setId(id);
        return this;
    }

    public StudentCourseItem courseName(String courseName) {
        setCourseName(courseName);
        return this;
    }

    public StudentCourseItem term(String term) {
        setTerm(term);
        return this;
    }

    public StudentCourseItem studentId(int studentId) {
        setStudentId(studentId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof StudentCourseItem)) {
            return false;
        }
        StudentCourseItem studentCourseItem = (StudentCourseItem) o;
        return id == studentCourseItem.id && Objects.equals(courseName, studentCourseItem.courseName) && Objects.equals(term, studentCourseItem.term) && studentId == studentCourseItem.studentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseName, term, studentId);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", courseName='" + getCourseName() + "'" +
            ", term='" + getTerm() + "'" +
            ", studentId='" + getStudentId() + "'" +
            "}";
    }
    
}
