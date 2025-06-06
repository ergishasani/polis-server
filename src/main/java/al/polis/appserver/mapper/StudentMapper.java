// File: src/main/java/al/polis/appserver/mapper/StudentMapper.java

package al.polis.appserver.mapper;

import al.polis.appserver.dto.StudentDto;
import al.polis.appserver.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper interface for converting between Student entities and StudentDto.
 *
 * Uses MapStruct to automatically generate the implementation.
 * The unmappedTargetPolicy=IGNORE ensures that any fields not explicitly mapped
 * (such as nested courses lists) are skipped without causing a compile error.
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { CourseMapper.class }  // if you want to map nested Course <-> CourseDto
)
public interface StudentMapper {

    /**
     * Convert a Student entity to a StudentDto.
     * Fields with matching names (id, firstName, lastName, email) are mapped automatically.
     * The courses field is ignored unless CourseMapper is provided.
     */
    StudentDto toDto(Student student);

    /**
     * Convert a StudentDto to a Student entity.
     * Fields with matching names are mapped automatically.
     */
    Student toEntity(StudentDto studentDto);
}
