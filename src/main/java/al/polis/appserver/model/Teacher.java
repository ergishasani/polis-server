// File: src/main/java/al/polis/appserver/model/Teacher.java

package al.polis.appserver.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Teacher entity in the database.
 */
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses;

    public Teacher() {
    }

    public Teacher(Long id, String firstName, String lastName, String email, List<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.courses = courses;
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    // ----------------------------
    // toString()
    // ----------------------------

    @Override
    public String toString() {
        int courseCount = (courses != null ? courses.size() : 0);
        return "Teacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", courseCount=" + courseCount +
                '}';
    }

    // ----------------------------
    // equals() & hashCode()
    // ----------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;
        if (id == null || teacher.id == null) return false;
        return Objects.equals(id, teacher.id);
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }
}
