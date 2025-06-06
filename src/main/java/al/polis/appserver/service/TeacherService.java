// src/main/java/al/polis/appserver/service/TeacherService.java

package al.polis.appserver.service;

import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.dto.TeacherDto;
import org.springframework.data.domain.Slice;

/**
 * Service interface for Teacher-related business logic.
 */
public interface TeacherService {
    /**
     * Create or update a teacher.
     *
     * @param teacherDto the teacher data transfer object
     * @return the saved or updated TeacherDto
     */
    TeacherDto upsertTeacher(TeacherDto teacherDto);

    /**
     * Retrieve a single teacher by its ID.
     *
     * @param teacherIdDto a DTO containing the teacher ID
     * @return the TeacherDto if found, otherwise null
     */
    TeacherDto getTeacher(LongIdDto teacherIdDto);

    /**
     * Delete a teacher by its ID.
     *
     * @param teacherIdDto a DTO containing the teacher ID
     */
    void deleteTeacher(LongIdDto teacherIdDto);

    /**
     * Filter teachers by a simple string and pagination info.
     *
     * @param filterDto a DTO containing the filter string and pagination
     * @return a Slice&lt;TeacherDto&gt; representing the requested page
     */
    Slice<TeacherDto> filterTeachers(SimpleStringFilterDto filterDto);
}
