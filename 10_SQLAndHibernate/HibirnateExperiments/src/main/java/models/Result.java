package models;
public class Result {
    public int studentId;
    public int courseId;
    /*public String studentName;
    public String courseName;*/

    public Result(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
        /*this.studentName = studentName;
        this.courseName = courseName;*/
    }

    public Result() {
    }

    @Override
    public String toString() {
        return "Result{" +
                "studentId=" + studentId +
                ", courseId=" + courseId; /*+
                ", studentName='" + studentName + '\'' +
                ", courseName='" + courseName + '\'' +
                '}';*/
    }

}
