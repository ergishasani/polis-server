package al.polis.appserver.communication;

import java.time.Instant;

/**
 * Represents a status message returned by the server, including an error code,
 * severity, message text, recommended action, help reference, and a generated trace ID.
 */
public class ServerStatus {

    private ServerErrorEnum code;
    private ErrorSeverityEnum severity;
    private String message;
    private String action;
    private String helpReference;
    private String traceId = "Java - Trace ID Not Available";

    /**
     * No-arg constructor for cases where you want an “empty” ServerStatus.
     * (Typically not used in code, but present for completeness.)
     */
    public ServerStatus() {
    }

    /**
     * Constructs a ServerStatus with explicit fields.
     *
     * @param code          the error/OK code
     * @param severity      the severity level of this status
     * @param message       a human-readable message
     * @param action        recommended action text
     */
    public ServerStatus(ServerErrorEnum code, ErrorSeverityEnum severity, String message, String action) {
        this.code = code;
        this.severity = severity;
        this.message = message;
        this.action = action;
        this.helpReference = "";
        populateTraceId();
    }

    /**
     * Constructs a ServerStatus including a help reference.
     *
     * @param code          the error/OK code
     * @param severity      the severity level
     * @param message       a human-readable message
     * @param action        recommended action text
     * @param helpReference a URL or pointer to additional documentation
     */
    public ServerStatus(ServerErrorEnum code, ErrorSeverityEnum severity, String message, String action, String helpReference) {
        this.code = code;
        this.severity = severity;
        this.message = message;
        this.action = action;
        this.helpReference = helpReference;
        populateTraceId();
    }

    /**
     * Constructs a ServerStatus based solely on an enum. Populates severity/message/action
     * based on that enum. You may need to adjust this if ServerErrorEnum does not carry
     * message/action/severity data.
     *
     * @param error the enum indicating which error or OK status to use
     */
    public ServerStatus(ServerErrorEnum error) {
        this.code = error;
        // If ServerErrorEnum contains its own severity/message/action, you can extract them here.
        // For this example, we assume a default severity and fill message/action from the enum’s
        // toString() or other methods. Adjust as needed.
        this.severity = ErrorSeverityEnum.INFO;            // Change if appropriate
        this.message = error.toString();                    // Or error.getMessage() if defined
        this.action = "";                                   // Or error.getAction() if defined
        this.helpReference = "";                            // Or error.getHelpReference() if defined
        populateTraceId();
    }

    /**
     * Generates a random trace ID based on the current time in milliseconds.
     */
    private void populateTraceId() {
        this.traceId = Long.toHexString(Instant.now().toEpochMilli());
    }

    /**
     * Factory method for an “unknown error” status.
     */
    public static ServerStatus createUnknownError() {
        return new ServerStatus(ServerErrorEnum.UNKNOWN_ERROR);
    }

    /**
     * Factory method for an “OK” (no error) status.
     */
    public static ServerStatus createNoError() {
        return new ServerStatus(ServerErrorEnum.OK);
    }

    // -----------------------------------
    // Explicit getters and setters below
    // -----------------------------------

    public ServerErrorEnum getCode() {
        return code;
    }

    public void setCode(ServerErrorEnum code) {
        this.code = code;
    }

    public ErrorSeverityEnum getSeverity() {
        return severity;
    }

    public void setSeverity(ErrorSeverityEnum severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getHelpReference() {
        return helpReference;
    }

    public void setHelpReference(String helpReference) {
        this.helpReference = helpReference;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    // --------------------
    // toString()
    // --------------------

    @Override
    public String toString() {
        return "ServerStatus{" +
                "code=" + code +
                ", severity=" + severity +
                ", message='" + message + '\'' +
                ", action='" + action + '\'' +
                ", helpReference='" + helpReference + '\'' +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}
