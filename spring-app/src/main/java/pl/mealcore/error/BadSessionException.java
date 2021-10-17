package pl.mealcore.error;

import java.io.Serial;

public final class BadSessionException extends Exception {

    @Serial
    private static final long serialVersionUID = 6174989789133347713L;

    public BadSessionException(final String message) {
        super(message);
    }

}
