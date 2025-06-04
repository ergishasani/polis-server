package al.polis.appserver.controller;

import al.polis.appserver.communication.ErrorContext;
import al.polis.appserver.communication.RespSingleDto;
import al.polis.appserver.communication.RespSliceDto;
import al.polis.appserver.dto.CourseStudentAssocDto;
import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.dto.StudentDto;
import al.polis.appserver.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Student-related operations.
 * Uses explicit constructor injection for StudentService.
 */
@RestController
@CrossOrigin("*")
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    /**
     * Constructor-based injection of StudentService.
     */
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Creates or updates a Student.
     * URL: POST /upsertStudent
     * Body: StudentDto
     */
    @PostMapping("/upsertStudent")
    @ResponseBody
    public RespSingleDto<StudentDto> upsertStudent(@RequestBody StudentDto student) {
        StudentDto res = null;
        try {
            res = studentService.upsertStudent(student);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }

    /**
     * Filters students by a simple string.
     * URL: POST /filterStudents
     * Body: SimpleStringFilterDto
     */
    @PostMapping("/filterStudents")
    @ResponseBody
    public RespSliceDto<StudentDto> filterStudents(@RequestBody SimpleStringFilterDto filter) {
        Slice<StudentDto> res = null;
        try {
            res = studentService.filterStudents(filter);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSliceDto<>(res, ErrorContext.readAndClean());
    }

    /**
     * Deletes a student by ID.
     * URL: POST /deleteStudent
     * Body: LongIdDto (wraps the student ID)
     */
    @PostMapping("/deleteStudent")
    @ResponseBody
    public RespSingleDto<Void> deleteStudent(@RequestBody LongIdDto studentId) {
        try {
            studentService.deleteStudent(studentId);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }

    /**
     * Associates a student to a course.
     * URL: POST /associateStudentToCourse
     * Body: CourseStudentAssocDto
     */
    @PostMapping("/associateStudentToCourse")
    @ResponseBody
    public RespSingleDto<Void> associateStudentToCourse(@RequestBody CourseStudentAssocDto assoc) {
        try {
            studentService.associateStudentToCourse(assoc);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }

    /**
     * Removes a student from a course.
     * URL: POST /removeStudentFromCourse
     * Body: CourseStudentAssocDto
     */
    @PostMapping("/removeStudentFromCourse")
    @ResponseBody
    public RespSingleDto<Void> removeStudentFromCourse(@RequestBody CourseStudentAssocDto assoc) {
        try {
            studentService.removeStudentFromCourse(assoc);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }

    /**
     * Retrieves a single student by ID.
     * URL: POST /getStudent
     * Body: LongIdDto (wraps the student ID)
     */
    @PostMapping("/getStudent")
    @ResponseBody
    public RespSingleDto<StudentDto> getStudent(@RequestBody LongIdDto studentId) {
        StudentDto res = null;
        try {
            res = studentService.getStudent(studentId);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }
}
