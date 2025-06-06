// src/main/java/al/polis/appserver/dto/LongIdDto.java

package al.polis.appserver.dto;

/**
 * Simple DTO carrying a single Long ID.
 */
public class LongIdDto {
    private Long id;

    /** No-arg constructor for JSON deserialization */
    public LongIdDto() {
    }

    /**
     * All-args constructor for convenience.
     *
     * @param id the Long identifier
     */
    public LongIdDto(Long id) {
        this.id = id;
    }

    /** Getter for id */
    public Long getId() {
        return id;
    }

    /** Setter for id */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LongIdDto{" +
                "id=" + id +
                '}';
    }
}
