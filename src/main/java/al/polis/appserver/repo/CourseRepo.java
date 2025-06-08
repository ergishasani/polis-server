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
     * Find all courses where title or description contains the given filter string
     * (case‐insensitive), returning a Page of Course objects.
     *
     * @param titleFilter       substring to match against title
     * @param descriptionFilter substring to match against description
     * @param pageable          pagination information
     * @return a page of matching Course objects
     */
    Page<Course> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String titleFilter,
            String descriptionFilter,
            Pageable pageable
    );
}
