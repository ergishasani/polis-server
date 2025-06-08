package al.polis.appserver.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import al.polis.appserver.dto.CourseDto;
import al.polis.appserver.dto.CourseTeacherAssocDto;
import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.model.Course;
import al.polis.appserver.model.Teacher;
import al.polis.appserver.repo.CourseRepo;
import al.polis.appserver.repo.StudentRepo;
import al.polis.appserver.repo.TeacherRepo;
import al.polis.appserver.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private StudentRepo studentRepo;

    // --- CREATE or UPDATE ---
    @Override
    public CourseDto upsertCourse(CourseDto dto) {
        Course course = (dto.getId() != null)
                ? courseRepo.findById(dto.getId()).orElse(new Course())
                : new Course();

        course.setCode(dto.getCode());
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setYear(dto.getYear());
        // (if dto carries a teacherId and you wish to set it here, you can fetch and set the teacher)

        Course saved = courseRepo.save(course);
        return toDto(saved);
    }

    // --- READ one ---
    @Override
    public CourseDto getCourse(LongIdDto idDto) {
        return courseRepo.findById(idDto.getId())
                .map(this::toDto)
                .orElse(null);
    }

    // --- DELETE ---
    @Override
    public void deleteCourse(LongIdDto idDto) {
        courseRepo.deleteById(idDto.getId());
    }

    // --- LIST / FILTER ---
    @Override
    public Slice<CourseDto> filterCourses(SimpleStringFilterDto filterDto) {
        int page = filterDto.getPagination().getPageNumber();
        int size = filterDto.getPagination().getPageSize();
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));

        Page<Course> ents = courseRepo
            .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                filterDto.getFilter(),
                filterDto.getFilter(),
                pageable
            );

        return new SliceImpl<>(
            ents.getContent().stream()
                .map(this::toDto)
                .collect(Collectors.toList()),
            pageable,
            ents.hasNext()
        );
    }

    // --- ASSOCIATE TEACHER (many-to-one) ---
    @Override
    public void associateTeacherToCourse(CourseTeacherAssocDto assocDto) {
        Course course = courseRepo.findById(assocDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Teacher teacher = teacherRepo.findById(assocDto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        course.setTeacher(teacher);
        courseRepo.save(course);
    }

    // --- REMOVE TEACHER ---
    @Override
    public void removeTeacherFromCourse(CourseTeacherAssocDto assocDto) {
        Course course = courseRepo.findById(assocDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // only clear if it matches the given teacherId
        if (course.getTeacher() != null
            && course.getTeacher().getId().equals(assocDto.getTeacherId())) {
            course.setTeacher(null);
            courseRepo.save(course);
        }
    }

    // --- HELPER: map Course entity → DTO ---
    private CourseDto toDto(Course e) {
        CourseDto dto = new CourseDto();
        dto.setId(e.getId());
        dto.setCode(e.getCode());
        dto.setTitle(e.getTitle());
        dto.setDescription(e.getDescription());
        dto.setYear(e.getYear());
        // (map teacher/students into dto if CourseDto supports them)
        return dto;
    }
}
