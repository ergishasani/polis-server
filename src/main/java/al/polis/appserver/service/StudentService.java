// src/main/java/al/polis/appserver/service/StudentService.java

package al.polis.appserver.service;

import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.dto.StudentDto;
import org.springframework.data.domain.Slice;

/**
 * Service interface for Student-related business logic.
 */
public interface StudentService {
    /**
     * Create or update a student.
     *
     * @param studentDto the student data transfer object
     * @return the saved or updated StudentDto
     */
    StudentDto upsertStudent(StudentDto studentDto);

    /**
     * Retrieve a single student by its ID.
     *
     * @param studentIdDto a DTO containing the student ID
     * @return the StudentDto if found, otherwise null
     */
    StudentDto getStudent(LongIdDto studentIdDto);

    /**
     * Delete a student by its ID.
     *
     * @param studentIdDto a DTO containing the student ID
     */
    void deleteStudent(LongIdDto studentIdDto);

    /**
     * Filter students by a simple string and pagination info.
     *
     * @param filterDto a DTO containing the filter string and pagination
     * @return a Slice&lt;StudentDto&gt; representing the requested page
     */
    Slice<StudentDto> filterStudents(SimpleStringFilterDto filterDto);
}
