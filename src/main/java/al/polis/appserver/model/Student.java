// File: src/main/java/al/polis/appserver/model/Student.java

package al.polis.appserver.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a Student entity in the database.
 */
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Student() {
    }

    public Student(Long id, String firstName, String lastName, String email, Course course) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.course = course;
    }

    // ----------------------------
    // Getters and Setters
    // ----------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    // ----------------------------
    // toString()
    // ----------------------------

    @Override
    public String toString() {
        Long courseId = (course != null ? course.getId() : null);
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", courseId=" + courseId +
                '}';
    }

    // ----------------------------
    // equals() & hashCode()
    // ----------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;
        if (id == null || student.id == null) return false;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }
}
