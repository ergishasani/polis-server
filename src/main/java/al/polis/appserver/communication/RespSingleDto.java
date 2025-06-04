package al.polis.appserver.communication;

import java.util.Collections;
import java.util.List;

/**
 * A DTO that carries exactly one “data” object (of type T),
 * plus a list of ServerStatus messages.
 */
public class RespSingleDto<T> extends ResponseWithStatusDto {

    private T data;

    /** No-arg constructor: 'data' is null, and status defaults to null. */
    public RespSingleDto() {
    }

    /**
     * Build a RespSingleDto that wraps a single data object and
     * an empty list of statuses.
     */
    public RespSingleDto(T data) {
        this.data = data;
        super.setStatus(Collections.emptyList());
    }

    /**
     * Build a RespSingleDto that wraps a single data object and
     * a custom list of ServerStatus.
     */
    public RespSingleDto(T data, List<ServerStatus> lista) {
        this.data = data;
        super.setStatus(lista);
    }

    /**
     * Build a RespSingleDto with no 'data' (null) and only a list of statuses.
     */
    public RespSingleDto(List<ServerStatus> lista) {
        super.setStatus(lista);
    }

    /** Getter for data */
    public T getData() {
        return data;
    }

    /** Setter for data */
    public void setData(T data) {
        this.data = data;
    }
}
