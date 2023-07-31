package model;

public class StudentClassInfo {

    private int studentId;
    private String name;
    private double grade;
    private int gradeCount;


    public StudentClassInfo(int studentId, String name, double grade,
                            int gradeCount) {
        this.studentId = studentId;
        this.name = name;
        this.grade = grade;
        this.gradeCount = gradeCount;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getGradeCount() {
        return gradeCount;
    }

    public void setGradeCount(int gradeCount) {
        this.gradeCount = gradeCount;
    }

    @Override
    public String toString() {
        return "StudentClassInfo{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", gradeCount=" + gradeCount +
                '}';
    }
}
