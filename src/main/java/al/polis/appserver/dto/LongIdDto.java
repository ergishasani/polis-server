package al.polis.appserver.dto;

/**
 * A simple DTO that carries a single Long‐typed ID.
 */
public class LongIdDto {

    private Long id;

    /**
     * No‐arg constructor (needed for JSON deserialization).
     */
    public LongIdDto() {
    }

    /**
     * All‐args constructor to manually build the object.
     * @param id the Long ID
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

    /**
     * toString() override, useful for logging/debugging.
     */
    @Override
    public String toString() {
        return "LongIdDto{" +
                "id=" + id +
                '}';
    }
}
