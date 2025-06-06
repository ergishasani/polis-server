// src/main/java/al/polis/appserver/communication/ErrorContext.java

package al.polis.appserver.communication;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple context holder for collecting error messages (or other
 * contextual information) during a request. Uses a ThreadLocal to
 * keep errors isolated per-request.
 *
 * Controllers typically call ErrorContext.readAndClean() to retrieve
 * any accumulated errors and reset the context for the next request.
 */
public class ErrorContext {
    // ThreadLocal to store error messages (or other context) per thread/request
    private static final ThreadLocal<List<String>> errorsHolder = ThreadLocal.withInitial(ArrayList::new);

    /**
     * Adds an error message to the current context.
     *
     * @param message the error message to add
     */
    public static void addError(String message) {
        errorsHolder.get().add(message);
    }

    /**
     * Retrieves and clears the current list of error messages.
     *
     * @return a List of error messages (empty if none)
     */
    public static List<String> readAndClean() {
        List<String> currentErrors = new ArrayList<>(errorsHolder.get());
        errorsHolder.get().clear();
        return currentErrors;
    }

    /**
     * Clears any accumulated errors without returning them.
     * Useful if you want to abandon the error context.
     */
    public static void clear() {
        errorsHolder.get().clear();
    }
}
