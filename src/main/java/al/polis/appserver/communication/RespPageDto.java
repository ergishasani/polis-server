package al.polis.appserver.communication;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Wrapper for a Page<T> response plus a list of ServerStatus objects.
 */
public class RespPageDto<T> extends ResponseWithStatusDto {

    private Page<T> page;

    /**
     * No-arg constructor (needed for JSON deserialization).
     */
    public RespPageDto() {
    }

    /**
     * Constructor that accepts a Page<T> and a list of ServerStatus.
     *
     * @param page   the Spring Data Page result
     * @param status the list of server statuses to include
     */
    public RespPageDto(Page<T> page, List<ServerStatus> status) {
        super.setStatus(status);
        this.page = page;
    }

    /** Getter for page */
    public Page<T> getPage() {
        return page;
    }

    /** Setter for page */
    public void setPage(Page<T> page) {
        this.page = page;
    }

    /**
     * Override toString() if you want to log both page info and statuses.
     */
    @Override
    public String toString() {
        return "RespPageDto{" +
                "page=" + page +
                ", status=" + getStatus() +
                '}';
    }
}
