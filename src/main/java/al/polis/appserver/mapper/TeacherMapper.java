// File: src/main/java/al/polis/appserver/mapper/TeacherMapper.java

package al.polis.appserver.mapper;

import al.polis.appserver.dto.TeacherDto;
import al.polis.appserver.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper interface for converting between Teacher entities and TeacherDto.
 *
 * Uses MapStruct to automatically generate the implementation.
 * The unmappedTargetPolicy=IGNORE ensures that any fields not explicitly mapped
 * (e.g., nested collections) are simply skipped without causing a compile error.
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TeacherMapper {

    /**
     * Convert a Teacher entity to a TeacherDto.
     * All fields with matching names (id, firstName, lastName, email) are mapped automatically.
     * Any additional fields not present in TeacherDto are ignored.
     */
    TeacherDto toDto(Teacher teacher);

    /**
     * Convert a TeacherDto to a Teacher entity.
     * All fields with matching names are mapped automatically.
     */
    Teacher toEntity(TeacherDto teacherDto);
}
