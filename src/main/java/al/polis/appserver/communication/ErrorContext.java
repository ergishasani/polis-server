package al.polis.appserver.communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds thread‐local ServerStatus messages.
 */
public class ErrorContext {
    private static final Logger log = LoggerFactory.getLogger(ErrorContext.class);

    private static final ThreadLocal<List<ServerStatus>> listaStatus;

    static {
        listaStatus = new ThreadLocal<>();
    }

    /**
     * Adds a new ServerStatus (based on the given error enum) to the current thread’s list.
     */
    public static void addStatusMessage(ServerErrorEnum error) {
        ServerStatus status = new ServerStatus(error);
        List<ServerStatus> ls = listaStatus.get();
        if (ls == null) {
            ls = new ArrayList<>();
            listaStatus.set(ls);
        }

        ls.add(status);
        logTrace(status);
    }

    /**
     * Returns the current thread’s accumulated statuses and then clears them.
     */
    public static List<ServerStatus> readAndClean() {
        List<ServerStatus> lista = listaStatus.get();
        listaStatus.remove();
        if (lista == null) {
            lista = new ArrayList<>();
        }
        return lista;
    }

    private static void logTrace(ServerStatus ms) {
        log.info("Trace ID = {}", ms);
    }
}
