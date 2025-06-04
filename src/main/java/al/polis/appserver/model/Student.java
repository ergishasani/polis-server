package al.polis.appserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.hibernate.proxy.HibernateProxy;

/**
 * Represents a Student in the database.
 */
@Entity
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String serialNumber;

    @ManyToOne
    private Course course;

    /**
     * No-arg constructor required by JPA (for entity instantiation).
     */
    public Student() {
    }

    /**
     * All-args constructor if you ever want to manually create a Student.
     * (JPA itself only requires the no-arg constructor.)
     */
    public Student(Long id, String firstName, String lastName, String serialNumber, Course course) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.serialNumber = serialNumber;
        this.course = course;
    }

    // -------------------
    // Explicit getters & setters
    // -------------------

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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    // -------------------
    // toString() for easier logging/debugging
    // -------------------

    @Override
    public String toString() {
        // Only print the Course’s ID (if present) to avoid infinite loops
        Long courseId = (course != null ? course.getId() : null);
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", courseId=" + courseId +
                '}';
    }

    // -------------------
    // equals() & hashCode() (recommended for JPA entities)
    // -------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        // If Hibernate gave us a proxy, unwrap it:
        if (o instanceof HibernateProxy) {
            o = ((HibernateProxy) o).getHibernateLazyInitializer().getImplementation();
        }
        if (!(o instanceof Student)) return false;

        Student other = (Student) o;
        // Two new (unsaved) entities with null IDs are not considered equal
        if (id == null || other.id == null) {
            return false;
        }
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        // Only use the primary key if it’s non-null
        return (id != null ? id.hashCode() : 0);
    }
}
