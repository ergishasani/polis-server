// src/main/java/al/polis/appserver/service/impl/StudentServiceImpl.java

package al.polis.appserver.service.impl;

import al.polis.appserver.dto.LongIdDto;
import al.polis.appserver.dto.SimpleStringFilterDto;
import al.polis.appserver.dto.StudentDto;
import al.polis.appserver.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

/**
 * Implementation of StudentService.
 * (Stubbed methods—replace with real repository and conversion logic.)
 */
@Service
public class StudentServiceImpl implements StudentService {

    // TODO: Autowire your StudentRepo, CourseRepo, etc.
    // @Autowired
    // private StudentRepo studentRepo;

    @Override
    public StudentDto upsertStudent(StudentDto studentDto) {
        // TODO: Convert StudentDto to entity, save via repo, convert back to DTO
        return studentDto; // placeholder
    }

    @Override
    public StudentDto getStudent(LongIdDto studentIdDto) {
        Long id = studentIdDto.getId();
        // TODO: Fetch entity by ID, convert to DTO
        return new StudentDto(); // placeholder
    }

    @Override
    public void deleteStudent(LongIdDto studentIdDto) {
        Long id = studentIdDto.getId();
        // TODO: Delete entity by ID
    }

    @Override
    public Slice<StudentDto> filterStudents(SimpleStringFilterDto filterDto) {
        String filter = filterDto.getFilter();
        int page = filterDto.getPagination().getPageNumber();
        int size = filterDto.getPagination().getPageSize();

        // TODO: Query repo with filter + PageRequest.of(page, size)
        // Then convert Page<StudentEntity> to Slice<StudentDto>.
        // For now, return an empty Page (which implements Slice).
        return Page.<StudentDto>empty();
    }
}
