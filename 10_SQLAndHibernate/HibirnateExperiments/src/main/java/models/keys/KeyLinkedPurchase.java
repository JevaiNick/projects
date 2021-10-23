package models.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class KeyLinkedPurchase  implements Serializable {
    @Column(name = "student_id", insertable = false, updatable = false)
    int studentId;
    @Column(name = "course_id", insertable = false, updatable = false)
    int courseId;

    public KeyLinkedPurchase(int studentId, int courseId){
        this.studentId = studentId;
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyLinkedPurchase that = (KeyLinkedPurchase) o;
        return studentId == that.studentId &&
                courseId == that.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
