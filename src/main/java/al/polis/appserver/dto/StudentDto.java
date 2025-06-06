// src/main/java/al/polis/appserver/dto/StudentDto.java

package al.polis.appserver.dto;

import java.util.List;

/**
 * Data Transfer Object for Student.
 */
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<CourseDto> courses;  // List of courses the student is enrolled in

    /** No-arg constructor for JSON deserialization */
    public StudentDto() {
    }

    /**
     * All-args constructor for manual instantiation.
     */
    public StudentDto(
            Long id,
            String firstName,
            String lastName,
            String email,
            List<CourseDto> courses
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.courses = courses;
    }

    // ----------------------------------
    // Getters and Setters
    // ----------------------------------

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

    public List<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", courses=" + courses +
                '}';
    }
}
