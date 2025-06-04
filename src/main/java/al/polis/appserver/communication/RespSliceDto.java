package al.polis.appserver.communication;

import org.springframework.data.domain.Slice;

import java.util.Collections;
import java.util.List;

/**
 * A DTO that carries a Spring Data Slice<T> plus a list of ServerStatus messages.
 */
public class RespSliceDto<T> extends ResponseWithStatusDto {

    private Slice<T> slice;

    /** No-arg constructor (needed for JSON deserialization). */
    public RespSliceDto() {
    }

    /**
     * Wraps a Slice<T> with an empty status list.
     *
     * @param slice the Spring Data Slice of results
     */
    public RespSliceDto(Slice<T> slice) {
        this.slice = slice;
        super.setStatus(Collections.emptyList());
    }

    /**
     * Wraps a Slice<T> with a provided list of ServerStatus.
     *
     * @param slice  the Spring Data Slice of results
     * @param status the list of ServerStatus messages
     */
    public RespSliceDto(Slice<T> slice, List<ServerStatus> status) {
        this.slice = slice;
        super.setStatus(status);
    }

    /** Getter for slice */
    public Slice<T> getSlice() {
        return slice;
    }

    /** Setter for slice */
    public void setSlice(Slice<T> slice) {
        this.slice = slice;
    }

    @Override
    public String toString() {
        return "RespSliceDto{" +
                "slice=" + slice +
                ", status=" + getStatus() +
                '}';
    }
}
