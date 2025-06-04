package al.polis.appserver.dto;

import java.util.List;

/**
 * Data Transfer Object for Course.
 */
public class CourseDto {
    private Long id;
    private String code;
    private String title;
    private String description;
    private Integer year;
    private TeacherDto teacher;            // Use TeacherDto instead of Teacher entity
    private List<StudentDto> students;

    /**
     * No-arg constructor (needed for JSON deserialization).
     */
    public CourseDto() {
    }

    /**
     * All-args constructor for manual instantiation.
     */
    public CourseDto(Long id,
                     String code,
                     String title,
                     String description,
                     Integer year,
                     TeacherDto teacher,
                     List<StudentDto> students) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.year = year;
        this.teacher = teacher;
        this.students = students;
    }

    // -----------------------------------
    // Getters and Setters
    // -----------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public TeacherDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDto teacher) {
        this.teacher = teacher;
    }

    public List<StudentDto> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDto> students) {
        this.students = students;
    }

    // -----------------------------------
    // toString() for debugging
    // -----------------------------------

    @Override
    public String toString() {
        return "CourseDto{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", teacher=" + teacher +
                ", students=" + students +
                '}';
    }
}
