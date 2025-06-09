package al.polis.appserver.service.impl;

import al.polis.appserver.dto.EnrollmentDto;
import al.polis.appserver.mapper.EnrollmentMapper;
import al.polis.appserver.model.Course;
import al.polis.appserver.model.Student;
import al.polis.appserver.model.Enrollment;
import al.polis.appserver.repo.CourseRepo;
import al.polis.appserver.repo.StudentRepo;
import al.polis.appserver.repo.EnrollmentRepository;
import al.polis.appserver.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepo       studentRepo;
    private final CourseRepo        courseRepo;
    private final EnrollmentMapper  enrollmentMapper;

    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 StudentRepo studentRepo,
                                 CourseRepo  courseRepo,
                                 EnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepo = studentRepo;
        this.courseRepo  = courseRepo;
        this.enrollmentMapper = enrollmentMapper;
    }

    @Override
    public EnrollmentDto enroll(Long studentId, Long courseId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseId));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        Enrollment saved = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(saved);
    }

    @Override
    public List<EnrollmentDto> listByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId)
                .stream()
                .map(enrollmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentDto> listByCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(enrollmentMapper::toDto)
                .collect(Collectors.toList());
    }
}
