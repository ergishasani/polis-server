package al.polis.appserver.controller;

import al.polis.appserver.communication.ErrorContext;
import al.polis.appserver.communication.RespSingleDto;
import al.polis.appserver.communication.RespSliceDto;
import al.polis.appserver.dto.CourseDto;
import al.polis.appserver.dto.CourseTeacherAssocDto;
import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Course-related operations.
 * Uses explicit constructor injection for CourseService.
 */
@RestController
@CrossOrigin("*")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    // Injected via constructor
    private final CourseService courseService;

    /**
     * Constructor-based injection of CourseService.
     */
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Creates or updates a Course.
     * URL: POST /upsertCourse
     * Body: CourseDto
     */
    @PostMapping("/upsertCourse")
    @ResponseBody
    public RespSingleDto<CourseDto> upsertCourse(@RequestBody CourseDto course) {
        CourseDto res = null;
        try {
            res = courseService.upsertCourse(course);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }

    /**
     * Filters courses by a simple string (e.g., title or description).
     * URL: POST /filterCourses
     * Body: SimpleStringFilterDto
     */
    @PostMapping("/filterCourses")
    @ResponseBody
    public RespSliceDto<CourseDto> filterCourses(@RequestBody SimpleStringFilterDto filter) {
        Slice<CourseDto> res = null;
        try {
            res = courseService.filterCourses(filter);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSliceDto<>(res, ErrorContext.readAndClean());
    }

    /**
     * Deletes a course by its ID.
     * URL: POST /deleteCourse
     * Body: LongIdDto (containing the ID)
     */
    @PostMapping("/deleteCourse")
    @ResponseBody
    public RespSingleDto<Void> deleteCourse(@RequestBody LongIdDto courseId) {
        try {
            courseService.deleteCourse(courseId);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }

    /**
     * Retrieves a single course by ID.
     * URL: POST /getCourse
     * Body: LongIdDto (containing the ID)
     */
    @PostMapping("/getCourse")
    @ResponseBody
    public RespSingleDto<CourseDto> getCourse(@RequestBody LongIdDto courseId) {
        CourseDto res = null;
        try {
            res = courseService.getCourse(courseId);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }

    /**
     * Associates a teacher to a course.
     * URL: POST /associateTeacherToCourse
     * Body: CourseTeacherAssocDto
     */
    @PostMapping("/associateTeacherToCourse")
    @ResponseBody
    public RespSingleDto<Void> associateTeacherToCourse(@RequestBody CourseTeacherAssocDto assoc) {
        try {
            courseService.associateTeacherToCourse(assoc);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }

    /**
     * Removes a teacher from a course.
     * URL: POST /removeTeacherFromCourse
     * Body: CourseTeacherAssocDto
     */
    @PostMapping("/removeTeacherFromCourse")
    @ResponseBody
    public RespSingleDto<Void> removeTeacherFromCourse(@RequestBody CourseTeacherAssocDto assoc) {
        try {
            courseService.removeTeacherFromCourse(assoc);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }
}
