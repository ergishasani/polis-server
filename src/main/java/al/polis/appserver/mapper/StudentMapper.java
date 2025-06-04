package al.polis.appserver.mapper;

import al.polis.appserver.dto.CourseDto;
import al.polis.appserver.dto.StudentDto;
import al.polis.appserver.model.Student;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

/**
 * Map Student ↔ StudentDto.
 * We will not call back into CourseMapper automatically, to avoid the cycle.
 */
@Mapper(
        componentModel = "spring"
        // no "uses = { CourseMapper.class }" here
)
public interface StudentMapper {

    @Mapping(target = "course", ignore = true)
    StudentDto toDto(Student source);

    @InheritInverseConfiguration
    @Mapping(target = "course", ignore = true)
    Student toEntity(StudentDto dto);

    /**
     * If you need a full CourseDto inside your StudentDto, call CourseMapper manually in your service.
     * Or define a custom method that only maps ID & code, but does not go all the way back to Student.
     */
    default CourseDto mapCourseToCourseDto(al.polis.appserver.model.Course course) {
        if (course == null) {
            return null;
        }
        CourseDto cd = new CourseDto();
        cd.setId(course.getId());
        cd.setCode(course.getCode());
        cd.setTitle(course.getTitle());
        // Don’t set cd.students here to avoid looping back
        return cd;
    }
}
