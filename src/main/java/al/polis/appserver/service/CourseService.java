// src/main/java/al/polis/appserver/service/CourseService.java

package al.polis.appserver.service;

import al.polis.appserver.dto.CourseDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import org.springframework.data.domain.Slice;

/**
 * Service interface for Course-related business logic.
 */
public interface CourseService {
    /**
     * Create or update a course.
     *
     * @param courseDto the course data transfer object
     * @return the saved or updated CourseDto
     */
    CourseDto upsertCourse(CourseDto courseDto);

    /**
     * Retrieve a single course by its ID.
     *
     * @param courseIdDto a DTO containing the course ID
     * @return the CourseDto if found, otherwise null
     */
    CourseDto getCourse(al.polis.appserver.dto.LongIdDto courseIdDto);

    /**
     * Delete a course by its ID.
     *
     * @param courseIdDto a DTO containing the course ID
     */
    void deleteCourse(al.polis.appserver.dto.LongIdDto courseIdDto);

    /**
     * Filter courses by a simple string and pagination info.
     *
     * @param filterDto a DTO containing the filter string and pagination
     * @return a Slice&lt;CourseDto&gt; representing the requested page
     */
    Slice<CourseDto> filterCourses(SimpleStringFilterDto filterDto);

    /**
     * Associate a teacher to a course.
     *
     * @param assocDto a DTO containing courseId and teacherId
     */
    void associateTeacherToCourse(al.polis.appserver.dto.CourseTeacherAssocDto assocDto);

    /**
     * Remove a teacher from a course.
     *
     * @param assocDto a DTO containing courseId and teacherId
     */
    void removeTeacherFromCourse(al.polis.appserver.dto.CourseTeacherAssocDto assocDto);
}
