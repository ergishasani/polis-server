// File: src/main/java/al/polis/appserver/mapper/CourseMapper.java

package al.polis.appserver.mapper;

import al.polis.appserver.dto.CourseDto;
import al.polis.appserver.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper interface for converting between Course entities and CourseDto.
 *
 * Uses MapStruct to automatically generate the implementation.
 * The unmappedTargetPolicy=IGNORE ensures that any fields not explicitly mapped
 * (such as nested teacher or students lists) are skipped without causing a compile error.
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { TeacherMapper.class }  // if you want to map nested Teacher <-> TeacherDto
)
public interface CourseMapper {

    /**
     * Convert a Course entity to a CourseDto.
     * Fields with matching names (id, code, title, description, year) are mapped automatically.
     * The teacher field is mapped via TeacherMapper; students are ignored unless you provide a StudentMapper.
     */
    CourseDto toDto(Course course);

    /**
     * Convert a CourseDto to a Course entity.
     * Fields with matching names are mapped automatically.
     */
    Course toEntity(CourseDto courseDto);
}
