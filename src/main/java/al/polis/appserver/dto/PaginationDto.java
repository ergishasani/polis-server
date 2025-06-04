package al.polis.appserver.dto;

/**
 * Carries pagination information for slice-based queries (page number + page size).
 */
public class PaginationDto {
    private Integer pageNumber;
    private Integer pageSize;

    /** No-arg constructor (needed for JSON deserialization). */
    public PaginationDto() {
    }

    /**
     * All-args constructor for manual instantiation.
     *
     * @param pageNumber zero-based page index
     * @param pageSize   number of items per page
     */
    public PaginationDto(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /** Getter for pageNumber */
    public Integer getPageNumber() {
        return pageNumber;
    }

    /** Setter for pageNumber */
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    /** Getter for pageSize */
    public Integer getPageSize() {
        return pageSize;
    }

    /** Setter for pageSize */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PaginationDto{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }
}
