package pl.mealcore.error;

import java.io.Serial;

public final class UserWrongPasswordException extends Exception {

    @Serial
    private static final long serialVersionUID = 5154896994786354586L;

    public UserWrongPasswordException(final String message) {
        super(message);
    }

}
