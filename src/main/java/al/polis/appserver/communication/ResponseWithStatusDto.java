package al.polis.appserver.communication;

import java.util.List;

public class ResponseWithStatusDto {
    // This field holds the status messages.
    private List<ServerStatus> status;

    // Public no-arg constructor (optional, but recommended for frameworks)
    public ResponseWithStatusDto() {
    }

    // Getter for 'status'
    public List<ServerStatus> getStatus() {
        return status;
    }

    // Setter for 'status'
    public void setStatus(List<ServerStatus> status) {
        this.status = status;
    }
}
