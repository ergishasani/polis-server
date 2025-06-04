package al.polis.appserver.mapper;

import al.polis.appserver.dto.CourseDto;
import al.polis.appserver.dto.StudentDto;
import al.polis.appserver.model.Course;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Map Course ↔ CourseDto.
 * Notice: We will strip out the “cascading” call to StudentMapper here;
 * instead, we’ll manually map over `course.getStudents()` to just grab
 * student IDs (or a few fields) to avoid pulling in StudentMapper.
 */
@Mapper(
        componentModel = "spring"
        // no "uses = { StudentMapper.class }" here anymore
)
public interface CourseMapper {

    @Mapping(target = "students", ignore = true)
        // We choose to ignore the “students” list here or map only IDs manually.
    CourseDto toDto(Course source);

    @InheritInverseConfiguration
    @Mapping(target = "students", ignore = true)
    Course toEntity(CourseDto dto);

    /**
     * If you want to manually map Course → List<StudentDto> without pulling in the full StudentMapper
     * (for example, you only need each student’s ID and name), you can declare a helper:
     */
    default List<StudentDto> mapStudentsListToDtoList(List<al.polis.appserver.model.Student> students) {
        if (students == null) {
            return null;
        }
        return students.stream()
                .map(s -> {
                    StudentDto sd = new StudentDto();
                    sd.setId(s.getId());
                    sd.setFirstName(s.getFirstName());
                    sd.setLastName(s.getLastName());
                    // don’t set sd.course here to avoid circular injection
                    return sd;
                })
                .toList();
    }
}
