package al.polis.appserver.controller;

import al.polis.appserver.communication.ErrorContext;
import al.polis.appserver.communication.RespSingleDto;
import al.polis.appserver.communication.RespSliceDto;
import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.dto.TeacherDto;
import al.polis.appserver.mapper.TeacherMapper;
import al.polis.appserver.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Teacher-related operations.
 * Constructor-injects TeacherService and TeacherMapper.
 */
@RestController
@CrossOrigin("*")
public class TeacherController {

    private static final Logger log = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;

    /**
     * Constructor-based injection of TeacherService and TeacherMapper.
     */
    public TeacherController(TeacherService teacherService, TeacherMapper teacherMapper) {
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
    }

    /**
     * Creates or updates a Teacher.
     * URL: POST /upsertTeacher
     * Body: TeacherDto
     */
    @PostMapping("/upsertTeacher")
    @ResponseBody
    public RespSingleDto<TeacherDto> upsertTeacher(@RequestBody TeacherDto teacher) {
        TeacherDto res = null;
        try {
            res = teacherService.upsertTeacher(teacher);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }

    /**
     * Filters teachers by a simple string.
     * URL: POST /filterTeachers
     * Body: SimpleStringFilterDto
     */
    @PostMapping("/filterTeachers")
    @ResponseBody
    public RespSliceDto<TeacherDto> filterTeachers(@RequestBody SimpleStringFilterDto filter) {
        Slice<TeacherDto> res = null;
        try {
            res = teacherService.filterTeachers(filter);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSliceDto<>(res, ErrorContext.readAndClean());
    }

    /**
     * Deletes a teacher by ID.
     * URL: POST /deleteTeacher
     * Body: LongIdDto (wraps the teacher ID)
     */
    @PostMapping("/deleteTeacher")
    @ResponseBody
    public RespSingleDto<Void> deleteTeacher(@RequestBody LongIdDto teacherId) {
        try {
            teacherService.deleteTeacher(teacherId);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }

    /**
     * Retrieves a single teacher by ID.
     * URL: POST /getTeacher
     * Body: LongIdDto (wraps the teacher ID)
     */
    @PostMapping("/getTeacher")
    @ResponseBody
    public RespSingleDto<TeacherDto> getTeacher(@RequestBody LongIdDto teacherId) {
        TeacherDto res = null;
        try {
            res = teacherService.getTeacher(teacherId);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }
}
