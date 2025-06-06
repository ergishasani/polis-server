// src/main/java/al/polis/appserver/repo/TeacherRepo.java

package al.polis.appserver.repo;

import al.polis.appserver.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repo for Teacher entities.
 * Provides CRUD operations and paging/filtering by firstName or lastName.
 */
public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    /**
     * Find all teachers where first name or last name contains the given filter string (case‐insensitive),
     * returning a Page of Teacher entities.
     *
     * @param filter1  the substring to match against firstName
     * @param filter2  the substring to match against lastName
     * @param pageable pagination information
     * @return a page of matching Teacher entities
     */
    Page<Teacher> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String filter1,
            String filter2,
            Pageable pageable
    );
}
