package al.polis.appserver.service.impl;

import al.polis.appserver.communication.ErrorContext;
import al.polis.appserver.communication.ServerErrorEnum;
import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.dto.TeacherDto;
import al.polis.appserver.exception.TestServerRuntimeException;
import al.polis.appserver.mapper.TeacherMapper;
import al.polis.appserver.model.Teacher;
import al.polis.appserver.repo.CourseRepository;
import al.polis.appserver.repo.TeacherRepository;
import al.polis.appserver.service.TeacherService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of TeacherService without Lombok.
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final TeacherMapper teacherMapper;

    /**
     * Constructor-based injection (replaces Lombok’s @AllArgsConstructor).
     *
     * @param teacherRepository repository for Teacher entities
     * @param courseRepository  repository for Course entities
     * @param teacherMapper     mapper between Teacher and TeacherDto
     */
    public TeacherServiceImpl(
            TeacherRepository teacherRepository,
            CourseRepository courseRepository,
            TeacherMapper teacherMapper) {

        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherDto upsertTeacher(TeacherDto teacherDto) {
        if (teacherDto == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.TEACHER_MISSING);
            throw new TestServerRuntimeException("Teacher is null");
        }
        Teacher entity = teacherMapper.toEntity(teacherDto);
        Teacher saved = teacherRepository.save(entity);
        return teacherMapper.toDto(saved);
    }

    @Override
    public Slice<TeacherDto> filterTeachers(SimpleStringFilterDto filter) {
        if (filter == null || filter.getPagination() == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.TEACHER_MISSING);
            throw new TestServerRuntimeException("Filter is null or has no pagination info.");
        }

        Slice<Teacher> teachersSlice;
        if (filter.getFilter() == null || filter.getFilter().isEmpty()) {
            teachersSlice = teacherRepository.findAll(PageRequest.of(0, 20));
        } else {
            String criterion = filter.getFilter();
            int pageNumber = filter.getPagination().getPageNumber();
            int pageSize = filter.getPagination().getPageSize();
            teachersSlice = teacherRepository.findByFirstNameContainsOrLastNameContains(
                    criterion,
                    criterion,
                    criterion,
                    PageRequest.of(pageNumber, pageSize));
        }

        List<TeacherDto> dtos = teachersSlice.stream()
                .map(teacherMapper::toDto)
                .toList();

        return new SliceImpl<>(dtos, teachersSlice.getPageable(), teachersSlice.hasNext());
    }

    @Override
    public void deleteTeacher(LongIdDto teacherId) {
        if (teacherId == null || teacherId.getId() == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.TEACHER_MISSING);
            throw new TestServerRuntimeException("Teacher id is null " + teacherId);
        }

        Teacher teacher = teacherRepository.findById(teacherId.getId()).orElse(null);
        if (teacher == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.TEACHER_NOT_FOUND);
            throw new TestServerRuntimeException("Teacher id not found " + teacherId);
        }

        if (teacher.getCourses() != null && !teacher.getCourses().isEmpty()) {
            ErrorContext.addStatusMessage(ServerErrorEnum.DELETE_TEACHER_NOT_ALLOWED);
            throw new TestServerRuntimeException("Teacher has courses and cannot be deleted.");
        }

        teacherRepository.delete(teacher);
    }

    @Override
    public TeacherDto getTeacher(LongIdDto teacherId) {
        if (teacherId == null || teacherId.getId() == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.TEACHER_MISSING);
            throw new TestServerRuntimeException("Teacher id is null " + teacherId);
        }

        Teacher teacher = teacherRepository.findById(teacherId.getId()).orElse(null);
        if (teacher == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.TEACHER_NOT_FOUND);
            throw new TestServerRuntimeException("Teacher id not found " + teacherId);
        }

        return teacherMapper.toDto(teacher);
    }
}
