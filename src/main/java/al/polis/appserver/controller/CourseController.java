// File: src/main/java/al/polis/appserver/controller/CourseController.java

package al.polis.appserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import al.polis.appserver.communication.ErrorContext;
import al.polis.appserver.communication.RespSingleDto;
import al.polis.appserver.communication.RespSliceDto;
import al.polis.appserver.dto.CourseDto;
import al.polis.appserver.dto.CourseTeacherAssocDto;
import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.PaginationDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.service.CourseService;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * GET /api/courses?page={page}&size={size}&filter={filter}
     */
    @GetMapping
    public RespSliceDto<CourseDto> listCourses(
            @RequestParam(defaultValue = "0") String page,
            @RequestParam(defaultValue = "20") String size,
            @RequestParam(defaultValue = "") String filter
    ) {
        // build filter + pagination DTO
        SimpleStringFilterDto dto = new SimpleStringFilterDto();
        dto.setFilter(filter);
        dto.setPagination(new PaginationDto(Integer.parseInt(page), Integer.parseInt(size)));

        Slice<CourseDto> slice = courseService.filterCourses(dto);
        return new RespSliceDto<>(slice, ErrorContext.readAndClean());
    }

    /**
     * GET /api/courses/{id}
     */
    @GetMapping("/{id}")
    public RespSingleDto<CourseDto> getCourse(@PathVariable Long id) {
        CourseDto course = courseService.getCourse(new LongIdDto(id));
        return new RespSingleDto<>(course, ErrorContext.readAndClean());
    }

    /**
     * POST /api/courses
     */
    @PostMapping
    public RespSingleDto<CourseDto> createCourse(@RequestBody CourseDto dto) {
        CourseDto created = courseService.upsertCourse(dto);
        return new RespSingleDto<>(created, ErrorContext.readAndClean());
    }

    /**
     * PUT /api/courses/{id}
     */
    @PutMapping("/{id}")
    public RespSingleDto<CourseDto> updateCourse(
            @PathVariable Long id,
            @RequestBody CourseDto dto
    ) {
        dto.setId(id);
        CourseDto updated = courseService.upsertCourse(dto);
        return new RespSingleDto<>(updated, ErrorContext.readAndClean());
    }

    /**
     * DELETE /api/courses/{id}
     */
    @DeleteMapping("/{id}")
    public RespSingleDto<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(new LongIdDto(id));
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }

    /**
     * POST /api/courses/{id}/teachers
     */
    @PostMapping("/{id}/teachers")
    public RespSingleDto<Void> addTeacherToCourse(
            @PathVariable Long id,
            @RequestBody CourseTeacherAssocDto assoc
    ) {
        assoc.setCourseId(id);
        courseService.associateTeacherToCourse(assoc);
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }

    /**
     * DELETE /api/courses/{courseId}/teachers/{teacherId}
     */
    @DeleteMapping("/{courseId}/teachers/{teacherId}")
    public RespSingleDto<Void> removeTeacherFromCourse(
            @PathVariable Long courseId,
            @PathVariable Long teacherId
    ) {
        CourseTeacherAssocDto assoc = new CourseTeacherAssocDto(courseId, teacherId);
        courseService.removeTeacherFromCourse(assoc);
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }
}
