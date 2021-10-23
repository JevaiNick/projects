package models.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class KeyPurchase implements Serializable {
    @Column(name = "student_name", insertable = false, updatable = false)
    String studentName;
    @Column(name = "course_name", insertable = false, updatable = false)
    String courseName;

    public KeyPurchase(String studentName, String courseName) {
        this.studentName = studentName;
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyPurchase that = (KeyPurchase) o;
        return Objects.equals(studentName, that.studentName) &&
                Objects.equals(courseName, that.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentName, courseName);
    }
}
