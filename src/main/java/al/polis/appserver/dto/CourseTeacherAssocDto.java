package al.polis.appserver.dto;

/**
 * Carries the association between a teacher and a course (two ID fields).
 */
public class CourseTeacherAssocDto {

    private Long idTeacher;
    private Long idCourse;

    /** No‐arg constructor (needed for JSON deserialization). */
    public CourseTeacherAssocDto() {
    }

    /**
     * All‐args constructor if you want to instantiate manually.
     *
     * @param idTeacher the ID of the teacher
     * @param idCourse  the ID of the course
     */
    public CourseTeacherAssocDto(Long idTeacher, Long idCourse) {
        this.idTeacher = idTeacher;
        this.idCourse = idCourse;
    }

    /** Getter for idTeacher */
    public Long getIdTeacher() {
        return idTeacher;
    }

    /** Setter for idTeacher */
    public void setIdTeacher(Long idTeacher) {
        this.idTeacher = idTeacher;
    }

    /** Getter for idCourse */
    public Long getIdCourse() {
        return idCourse;
    }

    /** Setter for idCourse */
    public void setIdCourse(Long idCourse) {
        this.idCourse = idCourse;
    }

    /**
     * toString() override for easier logging/debugging.
     */
    @Override
    public String toString() {
        return "CourseTeacherAssocDto{" +
                "idTeacher=" + idTeacher +
                ", idCourse=" + idCourse +
                '}';
    }
}
