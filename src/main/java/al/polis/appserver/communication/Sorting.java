package al.polis.appserver.communication;

/**
 * Represents sorting parameters for queries.
 */
public class Sorting {

    private String field;
    private String direction;
    private Boolean ignoreCase;
    private String nullHandling;

    /** No-arg constructor (needed for JSON deserialization). */
    public Sorting() {
    }

    /**
     * All-args constructor for manual instantiation.
     *
     * @param field        the field name to sort by
     * @param direction    "ASC" or "DESC"
     * @param ignoreCase   whether to ignore case in sorting
     * @param nullHandling how to handle nulls ("NULLS_FIRST", "NULLS_LAST", etc.)
     */
    public Sorting(String field, String direction, Boolean ignoreCase, String nullHandling) {
        this.field = field;
        this.direction = direction;
        this.ignoreCase = ignoreCase;
        this.nullHandling = nullHandling;
    }

    // -----------------------------------
    // Getters and Setters
    // -----------------------------------

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Boolean getIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public String getNullHandling() {
        return nullHandling;
    }

    public void setNullHandling(String nullHandling) {
        this.nullHandling = nullHandling;
    }

    // -----------------------------------
    // toString()
    // -----------------------------------

    @Override
    public String toString() {
        return "Sorting{" +
                "field='" + field + '\'' +
                ", direction='" + direction + '\'' +
                ", ignoreCase=" + ignoreCase +
                ", nullHandling='" + nullHandling + '\'' +
                '}';
    }
}
