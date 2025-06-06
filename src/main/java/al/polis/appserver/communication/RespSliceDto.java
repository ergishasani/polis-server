// src/main/java/al/polis/appserver/communication/RespSliceDto.java

package al.polis.appserver.communication;

import org.springframework.data.domain.Slice;

/**
 * Wrapper for returning a paged slice of data objects along with any error context.
 *
 * @param <T> the type of the data objects being returned
 */
public class RespSliceDto<T> {
    private Slice<T> dataSlice;
    private Object errorContext; // Adjust the type if ErrorContext has a specific DTO form

    /** No-arg constructor for JSON deserialization */
    public RespSliceDto() {
    }

    /**
     * All-args constructor for convenience.
     *
     * @param dataSlice    the Slice&lt;T&gt; (e.g., a page of CourseDto, StudentDto, etc.)
     * @param errorContext the error context (possibly null or empty if no errors)
     */
    public RespSliceDto(Slice<T> dataSlice, Object errorContext) {
        this.dataSlice = dataSlice;
        this.errorContext = errorContext;
    }

    /** Getter for dataSlice */
    public Slice<T> getDataSlice() {
        return dataSlice;
    }

    /** Setter for dataSlice */
    public void setDataSlice(Slice<T> dataSlice) {
        this.dataSlice = dataSlice;
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
