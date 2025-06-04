// src/main/java/al/polis/appserver/dto/SimpleStringFilterDto.java
package al.polis.appserver.dto;

/**
 * Carries a simple string-based filter plus pagination information.
 */
public class SimpleStringFilterDto {
    private String filter;
    private PaginationDto pagination;

    /**
     * No-arg constructor (needed for JSON deserialization).
     */
    public SimpleStringFilterDto() { }

    /**
     * All-args constructor.
     *
     * @param filter     the string to filter by (e.g., search term)
     * @param pagination pagination details (page number and page size)
     */
    public SimpleStringFilterDto(String filter, PaginationDto pagination) {
        this.filter = filter;
        this.pagination = pagination;
    }

    /**
     * Returns the filter string.
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Sets the filter string.
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Returns the pagination details.
     */
    public PaginationDto getPagination() {
        return pagination;
    }

    /**
     * Sets the pagination details.
     */
    public void setPagination(PaginationDto pagination) {
        this.pagination = pagination;
    }
}
