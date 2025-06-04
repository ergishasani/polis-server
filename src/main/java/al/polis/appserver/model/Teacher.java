package al.polis.appserver.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Teacher entity in the database.
 */
@Entity
public class Teacher {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String title;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

    /**
     * No-arg constructor required by JPA.
     */
    public Teacher() {
    }

    /**
     * All-args constructor for manual instantiation.
     *
     * @param id         the teacher’s ID
     * @param firstName  first name
     * @param lastName   last name
     * @param title      academic title
     * @param courses    list of courses taught
     */
    public Teacher(Long id, String firstName, String lastName, String title, List<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.courses = courses;
    }

    // ------------
    // Getters/setters
    // ------------

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    // ------------
    // toString
    // ------------

    @Override
    public String toString() {
        // Avoid infinite recursion by not printing full Course objects
        return "Teacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", title='" + title + '\'' +
                ", coursesCount=" + (courses != null ? courses.size() : 0) +
                '}';
    }

    // ------------
    // equals & hashCode
    // ------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;
        // Two persisted entities are equal if they share the same non-null ID
        if (id == null || teacher.id == null) return false;
        return Objects.equals(id, teacher.id);
    }

    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
    }
}
