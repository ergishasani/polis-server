// File: src/main/java/al/polis/appserver/controller/CourseController.java

package al.polis.appserver.controller;

import al.polis.appserver.communication.ErrorContext;
import al.polis.appserver.communication.RespSingleDto;
import al.polis.appserver.communication.RespSliceDto;
import al.polis.appserver.dto.CourseDto;
import al.polis.appserver.dto.CourseTeacherAssocDto;
import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.PaginationDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Course-related operations.
 * We add GET endpoints so that the front end can fetch all courses
 * and fetch a single course by ID via HTTP GET.
 */
@RestController
@CrossOrigin("*")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // ------------------------------------------------------------------------
    // GET /getAllCourses?page={page}&size={size}
    // Returns a paged slice of CourseDto objects
    // ------------------------------------------------------------------------
    @GetMapping("/getAllCourses")
    public RespSliceDto<CourseDto> getAllCourses(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        Slice<CourseDto> res = null;
        try {
            // Build a SimpleStringFilterDto with empty filter and pagination
            SimpleStringFilterDto dto = new SimpleStringFilterDto();
            dto.setFilter("");   // empty filter string
            dto.setPagination(new PaginationDto(page, size));
            res = courseService.filterCourses(dto);
        } catch (Exception ex) {
            log.error("Error in getAllCourses: " + ex.getMessage(), ex);
        }
        return new RespSliceDto<>(res, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // GET /getCourse/{id}
    // Returns a single CourseDto wrapped in RespSingleDto
    // ------------------------------------------------------------------------
    @GetMapping("/getCourse/{id}")
    public RespSingleDto<CourseDto> getCourseById(@PathVariable("id") Long id) {
        CourseDto res = null;
        try {
            LongIdDto dto = new LongIdDto(id);
            res = courseService.getCourse(dto);
        } catch (Exception ex) {
            log.error("Error in getCourseById: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /upsertCourse  (Create or update a course)
    // ------------------------------------------------------------------------
    @PostMapping("/upsertCourse")
    @ResponseBody
    public RespSingleDto<CourseDto> upsertCourse(@RequestBody CourseDto course) {
        CourseDto res = null;
        try {
            res = courseService.upsertCourse(course);
        } catch (Exception ex) {
            log.error("Error in upsertCourse: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /filterCourses  (Filter courses by a string → returns paged slice)
    // ------------------------------------------------------------------------
    @PostMapping("/filterCourses")
    @ResponseBody
    public RespSliceDto<CourseDto> filterCourses(@RequestBody SimpleStringFilterDto filter) {
        Slice<CourseDto> res = null;
        try {
            res = courseService.filterCourses(filter);
        } catch (Exception ex) {
            log.error("Error in filterCourses: " + ex.getMessage(), ex);
        }
        return new RespSliceDto<>(res, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /deleteCourse  (Delete a course by ID)
    // ------------------------------------------------------------------------
    @PostMapping("/deleteCourse")
    @ResponseBody
    public RespSingleDto<Void> deleteCourse(@RequestBody LongIdDto courseId) {
        try {
            courseService.deleteCourse(courseId);
        } catch (Exception ex) {
            log.error("Error in deleteCourse: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /getCourse  (Retrieve a course by ID via POST)
    // Note: GET version exists above; this POST remains for compatibility.
    // ------------------------------------------------------------------------
    @PostMapping("/getCourse")
    @ResponseBody
    public RespSingleDto<CourseDto> getCourse(@RequestBody LongIdDto courseId) {
        CourseDto res = null;
        try {
            res = courseService.getCourse(courseId);
        } catch (Exception ex) {
            log.error("Error in POST getCourse: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /associateTeacherToCourse  (Associate a teacher to a course)
    // ------------------------------------------------------------------------
    @PostMapping("/associateTeacherToCourse")
    @ResponseBody
    public RespSingleDto<Void> associateTeacherToCourse(@RequestBody CourseTeacherAssocDto assoc) {
        try {
            courseService.associateTeacherToCourse(assoc);
        } catch (Exception ex) {
            log.error("Error in associateTeacherToCourse: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /removeTeacherFromCourse  (Remove a teacher from a course)
    // ------------------------------------------------------------------------
    @PostMapping("/removeTeacherFromCourse")
    @ResponseBody
    public RespSingleDto<Void> removeTeacherFromCourse(@RequestBody CourseTeacherAssocDto assoc) {
        try {
            courseService.removeTeacherFromCourse(assoc);
        } catch (Exception ex) {
            log.error("Error in removeTeacherFromCourse: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }
}
