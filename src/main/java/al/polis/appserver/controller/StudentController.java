package al.polis.appserver.controller;

import al.polis.appserver.communication.ErrorContext;
import al.polis.appserver.communication.RespSingleDto;
import al.polis.appserver.communication.RespSliceDto;
import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.PaginationDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.dto.StudentDto;
import al.polis.appserver.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Student-related operations.
 * Adds GET endpoints for listing (paged) and retrieving a single student by ID.
 */
@RestController
@CrossOrigin("*")
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ------------------------------------------------------------------------
    // GET /getAllStudents?page={page}&size={size}
    // Returns a paged slice of StudentDto objects
    // ------------------------------------------------------------------------
    @GetMapping("/getAllStudents")
    public RespSliceDto<StudentDto> getAllStudents(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        Slice<StudentDto> res = null;
        try {
            // Build a SimpleStringFilterDto with empty filter and pagination
            SimpleStringFilterDto dto = new SimpleStringFilterDto();
            dto.setFilter("");
            dto.setPagination(new PaginationDto(page, size));
            res = studentService.filterStudents(dto);
        } catch (Exception ex) {
            log.error("Error in getAllStudents: " + ex.getMessage(), ex);
        }
        return new RespSliceDto<>(res, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // GET /getStudent/{id}
    // Returns a single StudentDto wrapped in RespSingleDto
    // ------------------------------------------------------------------------
    @GetMapping("/getStudent/{id}")
    public RespSingleDto<StudentDto> getStudentById(@PathVariable("id") Long id) {
        StudentDto res = null;
        try {
            LongIdDto dto = new LongIdDto(id);
            res = studentService.getStudent(dto);
        } catch (Exception ex) {
            log.error("Error in getStudentById: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /upsertStudent  (Create or update a student)
    // ------------------------------------------------------------------------
    @PostMapping("/upsertStudent")
    @ResponseBody
    public RespSingleDto<StudentDto> upsertStudent(@RequestBody StudentDto student) {
        StudentDto res = null;
        try {
            res = studentService.upsertStudent(student);
        } catch (Exception ex) {
            log.error("Error in upsertStudent: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /filterStudents  (Filter students by a string → returns paged slice)
    // ------------------------------------------------------------------------
    @PostMapping("/filterStudents")
    @ResponseBody
    public RespSliceDto<StudentDto> filterStudents(@RequestBody SimpleStringFilterDto filter) {
        Slice<StudentDto> res = null;
        try {
            res = studentService.filterStudents(filter);
        } catch (Exception ex) {
            log.error("Error in filterStudents: " + ex.getMessage(), ex);
        }
        return new RespSliceDto<>(res, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /deleteStudent  (Delete a student by ID)
    // ------------------------------------------------------------------------
    @PostMapping("/deleteStudent")
    @ResponseBody
    public RespSingleDto<Void> deleteStudent(@RequestBody LongIdDto studentId) {
        try {
            studentService.deleteStudent(studentId);
        } catch (Exception ex) {
            log.error("Error in deleteStudent: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(null, ErrorContext.readAndClean());
    }

    // ------------------------------------------------------------------------
    // POST /getStudent  (Retrieve a student by ID via POST)
    // Note: GET version exists above; this POST remains for compatibility.
    // ------------------------------------------------------------------------
    @PostMapping("/getStudent")
    @ResponseBody
    public RespSingleDto<StudentDto> getStudent(@RequestBody LongIdDto studentId) {
        StudentDto res = null;
        try {
            res = studentService.getStudent(studentId);
        } catch (Exception ex) {
            log.error("Error in POST getStudent: " + ex.getMessage(), ex);
        }
        return new RespSingleDto<>(res, ErrorContext.readAndClean());
    }
}
