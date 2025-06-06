// src/main/java/al/polis/appserver/repo/StudentRepo.java

package al.polis.appserver.repo;

import al.polis.appserver.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repo for Student entities.
 * Provides CRUD operations and paging/filtering by firstName or lastName.
 */
public interface StudentRepo extends JpaRepository<Student, Long> {
    /**
     * Find all students where first name or last name contains the given filter string (case‐insensitive),
     * returning a Page of Student entities.
     *
     * @param filter1  the substring to match against firstName
     * @param filter2  the substring to match against lastName
     * @param pageable pagination information
     * @return a page of matching Student entities
     */
    Page<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String filter1,
            String filter2,
            Pageable pageable
    );
}
