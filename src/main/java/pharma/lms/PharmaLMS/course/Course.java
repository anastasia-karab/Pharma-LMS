package pharma.lms.PharmaLMS.course;

import javax.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String courseName;
    private String department;

    public Course() {
    }

    public Course(Long id,
                  String courseName,
                  String department) {
        this.id = id;
        this.courseName = courseName;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
