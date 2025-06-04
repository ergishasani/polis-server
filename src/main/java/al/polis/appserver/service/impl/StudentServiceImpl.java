package al.polis.appserver.service.impl;

import al.polis.appserver.communication.ErrorContext;
import al.polis.appserver.communication.ServerErrorEnum;
import al.polis.appserver.dto.CourseStudentAssocDto;
import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.dto.StudentDto;
import al.polis.appserver.exception.TestServerRuntimeException;
import al.polis.appserver.mapper.StudentMapper;
import al.polis.appserver.model.Course;
import al.polis.appserver.model.Student;
import al.polis.appserver.repo.CourseRepository;
import al.polis.appserver.repo.StudentRepository;
import al.polis.appserver.service.StudentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for Student-related operations.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;

    /**
     * Constructor-based injection replaces @AllArgsConstructor (Lombok).
     *
     * @param studentRepository repository for Student entities
     * @param courseRepository  repository for Course entities
     * @param studentMapper     mapper between Student and StudentDto
     */
    public StudentServiceImpl(
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            StudentMapper studentMapper) {

        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentDto upsertStudent(StudentDto studentDto) {
        if (studentDto == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.STUDENT_MISSING);
            throw new TestServerRuntimeException("Student is null");
        }
        Student entity = studentMapper.toEntity(studentDto);
        Student saved = studentRepository.save(entity);
        return studentMapper.toDto(saved);
    }

    @Override
    public Slice<StudentDto> filterStudents(SimpleStringFilterDto filter) {
        if (filter == null || filter.getPagination() == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.STUDENT_MISSING);
            throw new TestServerRuntimeException("Filter is null or has no pagination info.");
        }

        Slice<Student> studentsSlice;
        if (filter.getFilter() == null || filter.getFilter().isEmpty()) {
            studentsSlice = studentRepository.findAll(PageRequest.of(0, 20));
        } else {
            String criterion = filter.getFilter();
            int pageNumber = filter.getPagination().getPageNumber();
            int pageSize = filter.getPagination().getPageSize();
            studentsSlice = studentRepository.findByFirstNameContainsOrLastNameContains(
                    criterion,
                    criterion,
                    criterion,
                    PageRequest.of(pageNumber, pageSize));
        }

        List<StudentDto> dtos = studentsSlice.stream()
                .map(studentMapper::toDto)
                .toList();

        return new SliceImpl<>(dtos, studentsSlice.getPageable(), studentsSlice.hasNext());
    }

    @Override
    public void deleteStudent(LongIdDto studentId) {
        if (studentId == null || studentId.getId() == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.STUDENT_MISSING);
            throw new TestServerRuntimeException("Student id is null " + studentId);
        }

        Student student = studentRepository.findById(studentId.getId()).orElse(null);
        if (student == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.STUDENT_NOT_FOUND);
            throw new TestServerRuntimeException("Student id not found " + studentId);
        }

        if (student.getCourse() != null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.DELETE_COURSE_NOT_ALLOWED);
            throw new TestServerRuntimeException("Student has a course and cannot be deleted.");
        }

        studentRepository.delete(student);
    }

    @Override
    public void associateStudentToCourse(CourseStudentAssocDto assoc) {
        Long courseId = assoc.getIdCourse();
        Long studentId = assoc.getIdStudent();

        if (courseId == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_MISSING);
            throw new TestServerRuntimeException("Course id is null " + courseId);
        }
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_NOT_FOUND);
            throw new TestServerRuntimeException("Course id not found " + courseId);
        }

        if (studentId == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.STUDENT_MISSING);
            throw new TestServerRuntimeException("Student id is null " + studentId);
        }
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.STUDENT_NOT_FOUND);
            throw new TestServerRuntimeException("Student id not found " + studentId);
        }

        // Associate and save
        student.setCourse(course);
        studentRepository.save(student);

        List<Student> list = course.getStudents();
        if (list != null) {
            list.removeIf(s -> s != null && s.getId().equals(studentId));
            list.add(student);
        } else {
            list = new ArrayList<>();
            list.add(student);
            course.setStudents(list);
        }
        courseRepository.save(course);
    }

    @Override
    public void removeStudentFromCourse(CourseStudentAssocDto assoc) {
        Long courseId = assoc.getIdCourse();
        Long studentId = assoc.getIdStudent();

        if (courseId == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_MISSING);
            throw new TestServerRuntimeException("Course id is null " + courseId);
        }
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_NOT_FOUND);
            throw new TestServerRuntimeException("Course id not found " + courseId);
        }

        if (studentId == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.STUDENT_MISSING);
            throw new TestServerRuntimeException("Student id is null " + studentId);
        }
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.STUDENT_NOT_FOUND);
            throw new TestServerRuntimeException("Student id not found " + studentId);
        }

        // Disassociate and save
        student.setCourse(null);
        studentRepository.save(student);

        List<Student> list = course.getStudents();
        if (list != null) {
            list.removeIf(s -> s != null && s.getId().equals(studentId));
        } else {
            course.setStudents(new ArrayList<>());
        }
        courseRepository.save(course);
    }

    @Override
    public StudentDto getStudent(LongIdDto studentId) {
        if (studentId == null || studentId.getId() == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.STUDENT_MISSING);
            throw new TestServerRuntimeException("Student id is null " + studentId);
        }

        Student student = studentRepository.findById(studentId.getId()).orElse(null);
        if (student == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.STUDENT_NOT_FOUND);
            throw new TestServerRuntimeException("Student id not found " + studentId);
        }

        return studentMapper.toDto(student);
    }
}
