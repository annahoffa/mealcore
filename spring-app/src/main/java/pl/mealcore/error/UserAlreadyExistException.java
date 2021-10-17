package pl.mealcore.error;

import java.io.Serial;

public final class UserAlreadyExistException extends Exception {

    @Serial
    private static final long serialVersionUID = 3289357368085379538L;

    public UserAlreadyExistException(final String message) {
        super(message);
    }

}
