package al.polis.appserver.dto;

public class EnrollmentDto {
    private Long id;
    private Long studentId;
    private Long courseId;

    public EnrollmentDto() { }

    public EnrollmentDto(Long id, Long studentId, Long courseId) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "EnrollmentDto{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}
