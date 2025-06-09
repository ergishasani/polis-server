package al.polis.appserver.service;

import al.polis.appserver.dto.EnrollmentDto;
import java.util.List;

public interface EnrollmentService {

    /**
     * Enrolls a student in a course.
     */
    EnrollmentDto enroll(Long studentId, Long courseId);

    /**
     * Lists all enrollments for a given student.
     */
    List<EnrollmentDto> listByStudent(Long studentId);

    /**
     * Lists all enrollments for a given course.
     */
    List<EnrollmentDto> listByCourse(Long courseId);
}
