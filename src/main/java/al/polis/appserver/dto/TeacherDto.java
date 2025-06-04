package al.polis.appserver.dto;

import java.util.List;

/**
 * Data Transfer Object for Teacher.
 */
public class TeacherDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String title;
    private List<CourseDto> courses;

    /** No-arg constructor (needed for JSON deserialization). */
    public TeacherDto() {
    }

    /**
     * All-args constructor for manual instantiation.
     *
     * @param id         the teacher’s ID
     * @param firstName  first name
     * @param lastName   last name
     * @param title      academic title
     * @param courses    list of CourseDto objects
     */
    public TeacherDto(Long id, String firstName, String lastName, String title, List<CourseDto> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
        this.courses = courses;
    }

    // Getters and setters

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

    public List<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "TeacherDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", title='" + title + '\'' +
                ", courses=" + courses +
                '}';
    }
}
