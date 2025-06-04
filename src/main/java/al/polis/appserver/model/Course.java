package al.polis.appserver.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Course entity in the database.
 */
@Entity
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    private String code;
    private String title;
    private String description;
    private Integer year;

    @ManyToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "course")
    private List<Student> students;

    /**
     * No-arg constructor required by JPA.
     */
    public Course() {
    }

    /**
     * All-args constructor for manual instantiation.
     *
     * @param id          the course ID
     * @param code        the course code
     * @param title       the course title
     * @param description the course description
     * @param year        the year the course is offered
     * @param teacher     the assigned teacher
     * @param students    the list of enrolled students
     */
    public Course(Long id,
                  String code,
                  String title,
                  String description,
                  Integer year,
                  Teacher teacher,
                  List<Student> students) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.year = year;
        this.teacher = teacher;
        this.students = students;
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    // ----------------------------
    // toString()
    // ----------------------------

    @Override
    public String toString() {
        Long teacherId = (teacher != null ? teacher.getId() : null);
        int studentCount = (students != null ? students.size() : 0);
        return "Course{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", teacherId=" + teacherId +
                ", studentsCount=" + studentCount +
                '}';
    }

    // ----------------------------
    // equals() & hashCode()
    // ----------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;
        if (id == null || course.id == null) return false;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }
}
