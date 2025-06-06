// src/main/java/al/polis/appserver/dto/SimpleStringFilterDto.java

package al.polis.appserver.dto;

/**
 * Carries a simple text filter plus pagination info.
 * Used to filter courses (e.g., by title or description) with paging.
 */
public class SimpleStringFilterDto {
    private String filter;
    private PaginationDto pagination;

    /** No-arg constructor (needed for JSON deserialization). */
    public SimpleStringFilterDto() {
    }

    /**
     * All-args constructor for manual instantiation.
     *
     * @param filter     the text to filter on (e.g., course title/description)
     * @param pagination pagination information (page number + page size)
     */
    public SimpleStringFilterDto(String filter, PaginationDto pagination) {
        this.filter = filter;
        this.pagination = pagination;
    }

    /** Getter for filter text. */
    public String getFilter() {
        return filter;
    }

    /** Setter for filter text. */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /** Getter for pagination info. */
    public PaginationDto getPagination() {
        return pagination;
    }

    /** Setter for pagination info. */
    public void setPagination(PaginationDto pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "SimpleStringFilterDto{" +
                "filter='" + filter + '\'' +
                ", pagination=" + pagination +
                '}';
    }
}
