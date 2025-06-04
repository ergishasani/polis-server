package al.polis.appserver.dto;

/**
 * Data Transfer Object for a Student.
 */
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String serialNumber;
    private CourseDto course;

    /** No‐arg constructor (needed for JSON deserialization). */
    public StudentDto() { }

    /**
     * All‐args constructor, if you ever want to build one by hand.
     */
    public StudentDto(
            Long id,
            String firstName,
            String lastName,
            String serialNumber,
            CourseDto course) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.serialNumber = serialNumber;
        this.course = course;
    }

    // Getter for id
    public Long getId() {
        return id;
    }

    // Setter for id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for firstName
    public String getFirstName() {
        return firstName;
    }

    // Setter for firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter for lastName
    public String getLastName() {
        return lastName;
    }

    // Setter for lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter for serialNumber
    public String getSerialNumber() {
        return serialNumber;
    }

    // Setter for serialNumber
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    // Getter for course
    public CourseDto getCourse() {
        return course;
    }

    // Setter for course
    public void setCourse(CourseDto course) {
        this.course = course;
    }

    /**
     * Optional: override toString() for easier logging/debugging.
     */
    @Override
    public String toString() {
        return "StudentDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", course=" + course +
                '}';
    }

    /**
     * Optional: override equals() and hashCode() if you compare DTOs in collections.
     * You can generate these in your IDE. For brevity, they’re omitted here.
     */
}
