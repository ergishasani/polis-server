// File: src/main/java/al/polis/appserver/repo/CourseRepo.java

package al.polis.appserver.repo;

import al.polis.appserver.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repo for Course entities.
 * Provides CRUD operations and paging/filtering by title or description.
 */
public interface CourseRepo extends JpaRepository<Course, Long> {
    /**
     * Find all courses where title or description contains the given filter string (case‐insensitive),
     * returning a Page of Course entities.
     *
     * @param filter1  the substring to match against title
     * @param filter2  the substring to match against description
     * @param pageable pagination information
     * @return a page of matching Course entities
     */
    Page<Course> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String filter1,
            String filter2,
            Pageable pageable
    );
}
