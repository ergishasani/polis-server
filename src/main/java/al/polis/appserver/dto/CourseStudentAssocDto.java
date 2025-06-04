package al.polis.appserver.dto;

/**
 * Holds the two IDs needed to associate a student with a course.
 */
public class CourseStudentAssocDto {
    private Long idStudent;
    private Long idCourse;

    // No-arg constructor (used by Spring’s JSON → object deserialization)
    public CourseStudentAssocDto() { }

    // All-args constructor (if you want to manually construct it)
    public CourseStudentAssocDto(Long idStudent, Long idCourse) {
        this.idStudent = idStudent;
        this.idCourse = idCourse;
    }

    // Getter for idStudent
    public Long getIdStudent() {
        return idStudent;
    }

    // Setter for idStudent
    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }

    // Getter for idCourse
    public Long getIdCourse() {
        return idCourse;
    }

    // Setter for idCourse
    public void setIdCourse(Long idCourse) {
        this.idCourse = idCourse;
    }

    // (Optional) A toString() if you want readable logging output
    @Override
    public String toString() {
        return "CourseStudentAssocDto{" +
                "idStudent=" + idStudent +
                ", idCourse=" + idCourse +
                '}';
    }
}
