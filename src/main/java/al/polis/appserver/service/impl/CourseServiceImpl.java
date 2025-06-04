// src/main/java/al/polis/appserver/service/impl/CourseServiceImpl.java
package al.polis.appserver.service.impl;

import al.polis.appserver.communication.ErrorContext;
import al.polis.appserver.communication.ServerErrorEnum;
import al.polis.appserver.dto.CourseDto;
import al.polis.appserver.dto.CourseTeacherAssocDto;
import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.exception.TestServerRuntimeException;
import al.polis.appserver.mapper.CourseMapper;
import al.polis.appserver.model.Course;
import al.polis.appserver.model.Teacher;
import al.polis.appserver.repo.CourseRepository;
import al.polis.appserver.repo.TeacherRepository;
import al.polis.appserver.service.CourseService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of CourseService.
 */
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;

    /**
     * Constructor-based injection of repositories and mapper.
     */
    public CourseServiceImpl(CourseRepository courseRepository,
                             TeacherRepository teacherRepository,
                             CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.courseMapper = courseMapper;
    }

    /**
     * Creates or updates a course.
     * If the incoming CourseDto is null, adds a status and throws.
     */
    @Transactional
    @Override
    public CourseDto upsertCourse(CourseDto course) {
        if (course == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_MISSING);
            throw new TestServerRuntimeException("Course is null");
        }
        Course entity = courseMapper.toEntity(course);
        Course saved = courseRepository.save(entity);
        return courseMapper.toDto(saved);
    }

    /**
     * Filters courses by a simple string and pagination.
     */
    @Override
    public Slice<CourseDto> filterCourses(SimpleStringFilterDto filter) {
        if (filter == null || filter.getPagination() == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_MISSING);
            throw new TestServerRuntimeException("Filter is null or has no pagination info.");
        }

        String criterion = "";
        Slice<Course> courseSlice;

        // Retrieve page number/size from the DTO
        int pageNumber = filter.getPagination().getPageNumber();
        int pageSize   = filter.getPagination().getPageSize();

        if (filter.getFilter() == null || filter.getFilter().isEmpty()) {
            courseSlice = courseRepository.findAll(PageRequest.of(pageNumber, pageSize));
        } else {
            criterion = filter.getFilter();
            courseSlice = courseRepository
                .findByCodeContainsOrTitleContainsOrDescriptionContains(
                    criterion,
                    criterion,
                    criterion,
                    PageRequest.of(pageNumber, pageSize));
        }

        List<CourseDto> dtos = courseSlice.stream()
            .map(courseMapper::toDto)
            .toList();

        return new SliceImpl<>(dtos, courseSlice.getPageable(), courseSlice.hasNext());
    }

    /**
     * Deletes a course by ID; enforces “cannot delete if in use” rules.
     */
    @Override
    public void deleteCourse(LongIdDto courseId) {
        if (courseId == null || courseId.getId() == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_NOT_FOUND);
            throw new TestServerRuntimeException("Course id is null " + courseId);
        }

        Course course = courseRepository.findById(courseId.getId()).orElse(null);
        if (course == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_NOT_FOUND);
            throw new TestServerRuntimeException("Course id not found " + courseId);
        }

        // If the course has a teacher assigned, cannot delete
        if (course.getTeacher() != null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.DELETE_COURSE_NOT_ALLOWED);
            throw new TestServerRuntimeException("Course has teacher and cannot be deleted.");
        }

        // If the course has any students, cannot delete
        if (course.getStudents() != null && !course.getStudents().isEmpty()) {
            ErrorContext.addStatusMessage(ServerErrorEnum.DELETE_COURSE_NOT_ALLOWED);
            throw new TestServerRuntimeException("Course has students and cannot be deleted.");
        }

        courseRepository.delete(course);
    }

    /**
     * Retrieves a single course by ID.
     */
    @Override
    public CourseDto getCourse(LongIdDto courseId) {
        if (courseId == null || courseId.getId() == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_MISSING);
            throw new TestServerRuntimeException("Course id is null " + courseId);
        }

        Course course = courseRepository.findById(courseId.getId()).orElse(null);
        if (course == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_NOT_FOUND);
            throw new TestServerRuntimeException("Course id not found " + courseId);
        }

        return courseMapper.toDto(course);
    }

    /**
     * Associates a teacher to a course.
     */
    @Override
    public void associateTeacherToCourse(CourseTeacherAssocDto assoc) {
        Long courseId  = assoc.getIdCourse();
        Long teacherId = assoc.getIdTeacher();

        if (courseId == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_MISSING);
            throw new TestServerRuntimeException("Course id is null " + courseId);
        }

        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_NOT_FOUND);
            throw new TestServerRuntimeException("Course id not found " + courseId);
        }

        if (teacherId == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.TEACHER_MISSING);
            throw new TestServerRuntimeException("Teacher id is null " + teacherId);
        }

        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if (teacher == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.TEACHER_NOT_FOUND);
            throw new TestServerRuntimeException("Teacher id not found " + teacherId);
        }

        // Assign teacher to course and save
        course.setTeacher(teacher);
        courseRepository.save(course);

        // Update teacher’s courses list
        List<Course> list = teacher.getCourses();
        if (list != null) {
            // Remove any existing assignment for this course
            list.removeIf(c -> c.getId().equals(courseId));
            list.add(course);
        } else {
            list = new ArrayList<>();
            list.add(course);
            teacher.setCourses(list);
        }
        teacherRepository.save(teacher);
    }

    /**
     * Removes a teacher from a course.
     */
    @Override
    public void removeTeacherFromCourse(CourseTeacherAssocDto assoc) {
        Long courseId  = assoc.getIdCourse();
        Long teacherId = assoc.getIdTeacher();

        if (courseId == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_NOT_FOUND);
            throw new TestServerRuntimeException("Course id is null " + courseId);
        }

        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.COURSE_NOT_FOUND);
            throw new TestServerRuntimeException("Course id not found " + courseId);
        }

        if (teacherId == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.TEACHER_MISSING);
            throw new TestServerRuntimeException("Teacher id is null " + teacherId);
        }

        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if (teacher == null) {
            ErrorContext.addStatusMessage(ServerErrorEnum.TEACHER_NOT_FOUND);
            throw new TestServerRuntimeException("Teacher id not found " + teacherId);
        }

        // Remove teacher from course and save
        course.setTeacher(null);
        courseRepository.save(course);

        // Update teacher’s courses list
        List<Course> list = teacher.getCourses();
        if (list != null) {
            list.removeIf(c -> c.getId().equals(courseId));
        } else {
            teacher.setCourses(new ArrayList<>());
        }
        teacherRepository.save(teacher);
    }
}
