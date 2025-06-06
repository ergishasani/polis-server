// src/main/java/al/polis/appserver/service/impl/CourseServiceImpl.java

package al.polis.appserver.service.impl;

import al.polis.appserver.dto.CourseDto;
import al.polis.appserver.dto.CourseTeacherAssocDto;
import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

/**
 * Implementation of CourseService.
 * (Stubbed methods—replace with real repository and conversion logic.)
 */
@Service
public class CourseServiceImpl implements CourseService {

    // TODO: Autowire your CourseRepo, TeacherRepo, StudentRepo, etc.
    // @Autowired
    // private CourseRepo courseRepo;

    @Override
    public CourseDto upsertCourse(CourseDto courseDto) {
        // TODO: Convert CourseDto to entity, save via repo, convert back to DTO
        return courseDto; // placeholder
    }

    @Override
    public CourseDto getCourse(LongIdDto courseIdDto) {
        Long id = courseIdDto.getId();
        // TODO: Fetch entity by ID, convert to DTO
        return new CourseDto(); // placeholder
    }

    @Override
    public void deleteCourse(LongIdDto courseIdDto) {
        Long id = courseIdDto.getId();
        // TODO: Delete entity by ID
    }

    @Override
    public Slice<CourseDto> filterCourses(SimpleStringFilterDto filterDto) {
        String filter = filterDto.getFilter();
        int page = filterDto.getPagination().getPageNumber();
        int size = filterDto.getPagination().getPageSize();

        // TODO: Query repo with filter + PageRequest.of(page, size)
        // Then convert Page<CourseEntity> to Slice<CourseDto>.
        // For now, return an empty Page (which implements Slice).
        return Page.<CourseDto>empty();
    }

    @Override
    public void associateTeacherToCourse(CourseTeacherAssocDto assocDto) {
        Long courseId = assocDto.getCourseId();
        Long teacherId = assocDto.getTeacherId();
        // TODO: Fetch course & teacher entities, set relation, save course
    }

    @Override
    public void removeTeacherFromCourse(CourseTeacherAssocDto assocDto) {
        Long courseId = assocDto.getCourseId();
        Long teacherId = assocDto.getTeacherId();
        // TODO: Fetch course & teacher entities, remove relation, save course
    }
}
