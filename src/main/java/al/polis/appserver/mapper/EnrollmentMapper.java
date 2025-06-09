package al.polis.appserver.mapper;

import al.polis.appserver.dto.EnrollmentDto;
import al.polis.appserver.model.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "course.id",  target = "courseId")
    EnrollmentDto toDto(Enrollment enrollment);
}
