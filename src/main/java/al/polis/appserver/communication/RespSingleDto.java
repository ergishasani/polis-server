// src/main/java/al/polis/appserver/communication/RespSingleDto.java

package al.polis.appserver.communication;

/**
 * Wrapper for returning a single data object along with any error context.
 *
 * @param <T> the type of the data being returned
 */
public class RespSingleDto<T> {
    private T data;
    private Object errorContext; // Adjust the type if ErrorContext has a specific DTO form

    /** No-arg constructor for JSON deserialization */
    public RespSingleDto() {
    }

    /**
     * All-args constructor for convenience.
     *
     * @param data         the single data object (e.g., a CourseDto, StudentDto, etc.)
     * @param errorContext the error context (possibly null or empty if no errors)
     */
    public RespSingleDto(T data, Object errorContext) {
        this.data = data;
        this.errorContext = errorContext;
    }

    /** Getter for data */
    public T getData() {
        return data;
    }

    /** Setter for data */
    public void setData(T data) {
        this.data = data;
    }

    /** Getter for errorContext */
    public Object getErrorContext() {
        return errorContext;
    }

    /** Setter for errorContext */
    public void setErrorContext(Object errorContext) {
        this.errorContext = errorContext;
    }
}
