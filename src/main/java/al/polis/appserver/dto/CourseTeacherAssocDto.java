// src/main/java/al/polis/appserver/dto/CourseTeacherAssocDto.java

package al.polis.appserver.dto;

/**
 * DTO used to associate or dissociate a teacher with a course.
 */
public class CourseTeacherAssocDto {
    private Long courseId;
    private Long teacherId;

    /** No-arg constructor for JSON deserialization */
    public CourseTeacherAssocDto() {
    }

    /**
     * All-args constructor for convenience.
     *
     * @param courseId  the ID of the course
     * @param teacherId the ID of the teacher
     */
    public CourseTeacherAssocDto(Long courseId, Long teacherId) {
        this.courseId = courseId;
        this.teacherId = teacherId;
    }

    /** Getter for courseId */
    public Long getCourseId() {
        return courseId;
    }

    /** Setter for courseId */
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    /** Getter for teacherId */
    public Long getTeacherId() {
        return teacherId;
    }

    /** Setter for teacherId */
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "CourseTeacherAssocDto{" +
                "courseId=" + courseId +
                ", teacherId=" + teacherId +
                '}';
    }
}
