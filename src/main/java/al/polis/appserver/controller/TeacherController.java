package al.polis.appserver.controller;

import al.polis.appserver.communication.ErrorContext;
import al.polis.appserver.communication.RespSingleDto;
import al.polis.appserver.communication.RespSliceDto;
import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.PaginationDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.dto.TeacherDto;
import al.polis.appserver.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Teacher-related operations.
 * Adds GET endpoints for listing (paged) and retrieving a single teacher by ID.
 */
@RestController
@CrossOrigin("*")
public class TeacherController {

    private static final Logger log = LoggerFactory.getLogger(TeacherController.class);
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // ------------------------------------------------------------------------
    // GET /getAllTeachers?page={page}&size={size}
    // Returns a paged slice of TeacherDto objects
    // ------------------------------------------------------------------------
    @GetMapping("/getAllTeachers")
    public RespSliceDto<TeacherDto> getAllTeachers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        Slice<TeacherDto> res = null;
        try {
            SimpleStringFilterDto dto = new SimpleStringFilterDto();
            dto.setFilter("");                       // no filter string
            dto.setPagination(new PaginationDto(page, size));
            res = teacherService.filterTeachers(dto);
        } catch (Exception ex) {
            log.error("Error in getAllTeachers: " + ex.getMessage(), ex);
        }
        return new RespSliceDto<>(res, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // GET /getTeacher/{id}
    // Returns a single TeacherDto wrapped in RespSingleDto
    // ------------------------------------------------------------------------
    @GetMapping("/getTeacher/{id}")
    public RespSingleDto<TeacherDto> getTeacherById(@PathVariable("id") Long id) {
        TeacherDto res = null;
        try {
            LongIdDto dto = new LongIdDto(id);
            res = teacherService.getTeacher(dto);
        } catch (Exception ex) {
            log.error("Error in getTeacherById: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /upsertTeacher  (Create or update a teacher)
    // ------------------------------------------------------------------------
    @PostMapping("/upsertTeacher")
    @ResponseBody
    public RespSingleDto<TeacherDto> upsertTeacher(@RequestBody TeacherDto teacher) {
        TeacherDto res = null;
        try {
            res = teacherService.upsertTeacher(teacher);
        } catch (Exception ex) {
            log.error("Error in upsertTeacher: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /filterTeachers  (Filter teachers by a string → returns paged slice)
    // ------------------------------------------------------------------------
    @PostMapping("/filterTeachers")
    @ResponseBody
    public RespSliceDto<TeacherDto> filterTeachers(@RequestBody SimpleStringFilterDto filter) {
        Slice<TeacherDto> res = null;
        try {
            res = teacherService.filterTeachers(filter);
        } catch (Exception ex) {
            log.error("Error in filterTeachers: " + ex.getMessage(), ex);
        }
        return new RespSliceDto<>(res, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /deleteTeacher  (Delete a teacher by ID)
    // ------------------------------------------------------------------------
    @PostMapping("/deleteTeacher")
    @ResponseBody
    public RespSingleDto<Void> deleteTeacher(@RequestBody LongIdDto teacherId) {
        try {
            teacherService.deleteTeacher(teacherId);
        } catch (Exception ex) {
            log.error("Error in deleteTeacher: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /getTeacher  (Retrieve a teacher by ID via POST)
    // Note: GET version exists above; this POST remains for compatibility.
    // ------------------------------------------------------------------------
    @PostMapping("/getTeacher")
    @ResponseBody
    public RespSingleDto<TeacherDto> getTeacher(@RequestBody LongIdDto teacherId) {
        TeacherDto res = null;
        try {
            res = teacherService.getTeacher(teacherId);
        } catch (Exception ex) {
            log.error("Error in POST getTeacher: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }
}
