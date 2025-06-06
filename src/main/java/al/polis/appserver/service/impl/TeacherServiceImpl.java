// src/main/java/al/polis/appserver/service/impl/TeacherServiceImpl.java

package al.polis.appserver.service.impl;

import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.dto.TeacherDto;
import al.polis.appserver.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

/**
 * Implementation of TeacherService.
 * (Stubbed methods—replace with real repository and conversion logic.)
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    // TODO: Autowire your TeacherRepo, CourseRepo, etc.
    // @Autowired
    // private TeacherRepo teacherRepo;

    @Override
    public TeacherDto upsertTeacher(TeacherDto teacherDto) {
        // TODO: Convert TeacherDto to entity, save via repo, convert back to DTO
        return teacherDto; // placeholder
    }

    @Override
    public TeacherDto getTeacher(LongIdDto teacherIdDto) {
        Long id = teacherIdDto.getId();
        // TODO: Fetch entity by ID, convert to DTO
        return new TeacherDto(); // placeholder
    }

    @Override
    public void deleteTeacher(LongIdDto teacherIdDto) {
        Long id = teacherIdDto.getId();
        // TODO: Delete entity by ID
    }

    @Override
    public Slice<TeacherDto> filterTeachers(SimpleStringFilterDto filterDto) {
        String filter = filterDto.getFilter();
        int page = filterDto.getPagination().getPageNumber();
        int size = filterDto.getPagination().getPageSize();

        // TODO: Query repo with filter + PageRequest.of(page, size)
        // Then convert Page<TeacherEntity> to Slice<TeacherDto>.
        // For now, return an empty Page (which implements Slice).
        return Page.<TeacherDto>empty();
    }
}
