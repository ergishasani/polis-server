package al.polis.appserver.controller;

import al.polis.appserver.communication.ErrorContext;
import al.polis.appserver.communication.RespSingleDto;
import al.polis.appserver.dto.EnrollmentDto;
import al.polis.appserver.service.EnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final Logger log = LoggerFactory.getLogger(EnrollmentController.class);
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    /**
     * POST /api/enrollments/students/{studentId}/courses/{courseId}
     */
    @PostMapping("/students/{studentId}/courses/{courseId}")
    public RespSingleDto<EnrollmentDto> enrollStudentInCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ) {
        EnrollmentDto dto = null;
        try {
            dto = enrollmentService.enroll(studentId, courseId);
        } catch (Exception ex) {
            log.error("Error enrolling student {} in course {}: {}", studentId, courseId, ex.getMessage(), ex);
        }
        return new RespSingleDto<>(dto, ErrorContext.readAndClean());
    }
}
