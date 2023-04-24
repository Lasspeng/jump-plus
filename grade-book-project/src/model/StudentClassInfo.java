package model;

public class StudentClassInfo {

    private String name;
    private double grade;
    private int gradeCount;

    public StudentClassInfo(String name, double grade, int gradeCount) {
        this.name = name;
        this.grade = grade;
        this.gradeCount = gradeCount;
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
}
